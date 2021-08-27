package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class SubmissionsFetcher(

    private val api: RedditApi,
    private val subreddit: String,

    private var sorting: SubmissionsSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> Map<String, String>

) : Fetcher<Submission>(limit) {

    companion object {

        val DEFAULT_SORTING = SubmissionsSorting.HOT
        val DEFAULT_TIMEPERIOD = TimePeriod.LAST_DAY
    }

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<Submission>? {

        val req = if (subreddit != "") {
            api.fetchSubmissions(
                subreddit = subreddit,
                sorting = getSorting().sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = getLimit(),
                count = getCount(),
                before = previousToken,
                after = nextToken,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = getHeader()
            )
        } else {
            api.fetchSubmissions(
                sorting = getSorting().sortingStr,
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
