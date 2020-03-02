package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubredditListing
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.general.SubredditSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod

class SubredditsSearchFetcher(

    private val api: RedditApi,
    private val query: String,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: SubredditSearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Subreddit, EnvelopedSubreddit>(limit) {

    companion object {

        val DEFAULT_SORTING = SubredditSearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubreddit>? {

        val req = api.fetchSubredditsSearch(
            query = query,
            sorting = getSorting().sortingStr,
            timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
            limit = getLimit(),
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedSubreddit>?): List<Subreddit> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as SubredditListing)
            .children
            .map { it.data }
            .toList()
    }

    fun getSorting(): SubredditSearchSorting = sorting
    fun setSorting(newSorting: SubredditSearchSorting) {
        sorting = newSorting

        reset()
    }

    fun getTimePeriod(): TimePeriod = timePeriod
    fun setTimePeriod(newTimePeriod: TimePeriod) {
        timePeriod = newTimePeriod

        reset()
    }
}
