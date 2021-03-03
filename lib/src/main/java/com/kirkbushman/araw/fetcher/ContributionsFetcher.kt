package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.base.Contribution

class ContributionsFetcher(

    private val api: RedditApi,
    private val username: String? = null,
    private val where: String,

    private var sorting: ContributionsSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getUsername: (() -> String)? = null,
    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Contribution>(limit) {

    companion object {

        val DEFAULT_SORTING = ContributionsSorting.NEW
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    private var usernameToFetch: String? = null

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<Contribution>? {

        if (username == null && getUsername == null) {
            throw IllegalStateException("username and getUsername cannot both be null!")
        }

        if (username != null) {
            usernameToFetch = username
        }

        if (getUsername != null) {
            usernameToFetch = getUsername.invoke()
        }

        val req = if (where == "") {

            api.fetchRedditorOverview(
                username = usernameToFetch!!,
                sorting = sorting.sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = getLimit(),
                count = getCount(),
                before = previousToken,
                after = nextToken,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = getHeader()
            )
        } else {

            api.fetchRedditorInfo(
                username = usernameToFetch!!,
                where = where,
                sorting = sorting.sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = getLimit(),
                count = getCount(),
                before = previousToken,
                after = nextToken,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = getHeader()
            )
        }

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        val resultBody = res.body()
        setTokens(resultBody?.data?.before, resultBody?.data?.after)

        return resultBody
            ?.data
            ?.children
            ?.map { it.data }
            ?.toList()
    }

    fun getSorting(): ContributionsSorting = sorting
    fun setSorting(newSorting: ContributionsSorting) {
        sorting = newSorting

        reset()
    }

    fun getTimePeriod(): TimePeriod = timePeriod
    fun setTimePeriod(newTimePeriod: TimePeriod) {
        timePeriod = newTimePeriod

        reset()
    }

    fun requiresTimePeriod(): Boolean {
        return getSorting().requiresTimePeriod
    }
}
