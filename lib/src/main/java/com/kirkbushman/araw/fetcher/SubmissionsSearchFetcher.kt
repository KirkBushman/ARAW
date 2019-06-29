package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubmissionListing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.SearchSorting
import com.kirkbushman.araw.models.general.TimePeriod

class SubmissionsSearchFetcher(

    private val api: RedditApi,
    private val subreddit: String,
    private val query: String,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: SearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Submission, EnvelopedSubmission>(limit) {

    companion object {

        val DEFAULT_SORTING = SearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubmission>? {

        val req = api.fetchSubmissionsSearch(
            subreddit = subreddit,
            query = query,
            sorting = getSorting().sortingStr,
            timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
            limit = if (forward) getLimit() else getLimit() + 1,
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

    override fun onMapResult(pagedData: Listing<EnvelopedSubmission>?): List<Submission> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as SubmissionListing)
            .children
            .map { it.data }
            .toList()
    }

    fun getSorting(): SearchSorting {
        return sorting
    }

    fun setSorting(newSorting: SearchSorting) {
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

    override fun toString(): String {
        return "SubmissionsSearchFetcher { " +
                "subreddit: $subreddit, " +
                "sorting: $sorting, " +
                "timePeriod: $timePeriod, " +
                "${super.toString()} " +
                "}"
    }
}