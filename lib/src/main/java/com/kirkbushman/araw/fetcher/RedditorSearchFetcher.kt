package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.araw.models.enums.RedditorSearchSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class RedditorSearchFetcher(

    private val api: RedditApi,
    private val query: String,

    private var sorting: RedditorSearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val showAll: Boolean = false,
    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> Map<String, String>

) : Fetcher<RedditorData>(limit) {

    companion object {

        val DEFAULT_SORTING = RedditorSearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<RedditorData>? {

        val req = api.fetchRedditorSearch(
            query = query,
            show = if (showAll) "all" else null,
            sorting = getSorting().sortingStr,
            timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
            limit = getLimit(),
            count = getCount(),
            before = previousToken,
            after = nextToken,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

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

    fun getSorting(): RedditorSearchSorting = sorting
    fun setSorting(newSorting: RedditorSearchSorting) {
        sorting = newSorting

        reset()
    }

    fun getTimePeriod(): TimePeriod = timePeriod
    fun setTimePeriod(newTimePeriod: TimePeriod) {
        timePeriod = newTimePeriod

        reset()
    }
}
