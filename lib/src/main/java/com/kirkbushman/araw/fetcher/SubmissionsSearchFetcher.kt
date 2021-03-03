package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SearchSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class SubmissionsSearchFetcher(

    private val api: RedditApi,
    private val subreddit: String?,
    private val query: String,

    private var sorting: SearchSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val showAll: Boolean = false,
    private val restrictToSubreddit: Boolean = false,
    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Submission>(limit) {

    companion object {

        val DEFAULT_SORTING = SearchSorting.RELEVANCE
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<Submission>? {

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
                    before = previousToken,
                    after = nextToken,
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
                    before = previousToken,
                    after = nextToken,
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
                limit = if (nextToken != null) getLimit() else getLimit() + 1,
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
