package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedContribution
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.ContributionListing
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.mixins.Contribution

class ContributionsFetcher(

    private val api: RedditApi,
    private val username: String? = null,
    private val where: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private var sorting: ContributionsSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getUsername: (() -> String)? = null,
    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Contribution, EnvelopedContribution>(limit) {

    companion object {
        val DEFAULT_SORTING = ContributionsSorting.NEW
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    private var usernameToFetch: String? = null

    @WorkerThread
    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedContribution>? {

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
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
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
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = getHeader()
            )
        }

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedContribution>?): List<Contribution> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as ContributionListing)
            .children
            .map { it.data }
            .toList()
    }

    fun getSorting(): ContributionsSorting {
        return sorting
    }

    fun setSorting(newSorting: ContributionsSorting) {
        sorting = newSorting

        reset()
    }

    fun getTimePeriod(): TimePeriod {
        return timePeriod
    }

    fun setTimePeriod(newTimePeriod: TimePeriod) {
        timePeriod = newTimePeriod

        reset()
    }

    fun requiresTimePeriod(): Boolean {
        return getSorting().requiresTimePeriod
    }
}
