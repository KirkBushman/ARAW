package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubmissionListing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.Sorting
import com.kirkbushman.araw.models.general.TimePeriod

class SubmissionFetcher(

    private val api: RedditApi,
    private val subreddit: String,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: Sorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Submission, EnvelopedSubmission>(limit) {

    companion object {
        private val DEFAULT_SORTING = Sorting.HOT
        private val DEFAULT_TIMEPERIOD = TimePeriod.LAST_DAY
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubmission>? {

        val req = api.fetchSubmissions(
            subreddit = subreddit,
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

    fun getSorting(): Sorting {
        return sorting
    }

    fun setSorting(newSorting: Sorting) {
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

    override fun toString(): String {
        return "SubmissionFetcher { " +
                "subreddit: $subreddit, " +
                "sorting: $sorting, " +
                "timePeriod: $timePeriod, " +
                "${super.toString()} " +
                "}"
    }
}