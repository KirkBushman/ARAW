package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedRedditorData
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.RedditorDataListing
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.araw.models.enums.RedditorSearchSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class RedditorSearchFetcher(

    private val api: RedditApi,
    private val query: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private var sorting: RedditorSearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private val showAll: Boolean = false,
    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<RedditorData, EnvelopedRedditorData>(limit) {

    companion object {

        val DEFAULT_SORTING = RedditorSearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    @WorkerThread
    override fun onFetching(forward: Boolean, dirToken: String?): Listing<EnvelopedRedditorData>? {

        val req = api.fetchRedditorSearch(
            query = query,
            show = if (showAll) "all" else null,
            sorting = getSorting().sortingStr,
            timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
            limit = getLimit(),
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedRedditorData>?): List<RedditorData> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as RedditorDataListing)
            .children
            .map { it.data }
            .toList()
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
