package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubmissionListing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.TimePeriod

class SubmissionsFetcher(

    private val api: RedditApi,
    private val subreddit: String,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: SubmissionsSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Submission, EnvelopedSubmission>(limit) {

    companion object {

        val DEFAULT_SORTING = SubmissionsSorting.HOT
        val DEFAULT_TIMEPERIOD = TimePeriod.LAST_DAY
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubmission>? {

        val req = if (subreddit != "") {
            api.fetchSubmissions(
                subreddit = subreddit,
                sorting = getSorting().sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = getLimit(),
                count = getCount(),
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = getHeader()
            )
        } else {
            api.fetchSubmissions(
                sorting = getSorting().sortingStr,
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

    override fun onMapResult(pagedData: Listing<EnvelopedSubmission>?): List<Submission> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as SubmissionListing)
            .children
            .map { it.data }
            .toList()
    }

    fun getSorting(): SubmissionsSorting = sorting
    fun setSorting(newSorting: SubmissionsSorting) {
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
