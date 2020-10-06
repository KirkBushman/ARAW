package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubmissionListing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.SearchSorting
import com.kirkbushman.araw.models.general.TimePeriod

class SubmissionsSearchFetcher(

    private val api: RedditApi,
    private val subreddit: String?,
    private val query: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private var sorting: SearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private val showAll: Boolean = false,
    private val restrictToSubreddit: Boolean = false,
    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Submission, EnvelopedSubmission>(limit) {

    companion object {

        val DEFAULT_SORTING = SearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    @WorkerThread
    override fun onFetching(forward: Boolean, dirToken: String?): Listing<EnvelopedSubmission>? {

        val req = if (subreddit != null) {

            if (subreddit != "") {

                api.fetchSubmissionsSearch(
                    subreddit = subreddit,
                    query = query,
                    show = if (showAll) "all" else null,
                    sorting = getSorting().sortingStr,
                    timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                    limit = getLimit(),
                    count = getCount(),
                    after = if (forward) dirToken else null,
                    before = if (!forward) dirToken else null,
                    restrictToSubreddit = restrictToSubreddit,
                    rawJson = (if (disableLegacyEncoding) 1 else null),
                    header = getHeader()
                )
            } else {

                api.fetchFrontpageSubmissionsSearch(
                    query = query,
                    show = if (showAll) "all" else null,
                    sorting = getSorting().sortingStr,
                    timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                    limit = getLimit(),
                    count = getCount(),
                    after = if (forward) dirToken else null,
                    before = if (!forward) dirToken else null,
                    restrictToSubreddit = restrictToSubreddit,
                    rawJson = (if (disableLegacyEncoding) 1 else null),
                    header = getHeader()
                )
            }
        } else {
            api.fetchSubmissionsSearchGeneral(
                query = query,
                show = if (showAll) "all" else null,
                sorting = getSorting().sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = if (forward) getLimit() else getLimit() + 1,
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

    fun getSorting(): SearchSorting = sorting
    fun setSorting(newSorting: SearchSorting) {
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
