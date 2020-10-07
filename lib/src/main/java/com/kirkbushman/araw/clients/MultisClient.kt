package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.MultiSubmissionsFetcher
import com.kirkbushman.araw.models.Multi
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.TimePeriod

class MultisClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>
) {

    fun multiSubmissions(

        multi: Multi,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = MultiSubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = MultiSubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): MultiSubmissionsFetcher {

        return multiSubmissions(
            username = multi.ownerName,
            multiname = multi.name,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
        )
    }

    fun multiSubmissions(

        username: String,
        multiname: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = MultiSubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = MultiSubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): MultiSubmissionsFetcher {

        return MultiSubmissionsFetcher(
            api = api,
            username = username,
            multiname = multiname,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    @WorkerThread
    fun myMultis(
        disableLegacyEncoding: Boolean = false
    ): List<Multi>? {

        val authMap = getHeaderMap()
        val req = api.myMultis(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.map { it.data }
    }

    @WorkerThread
    fun redditorMultis(
        username: String,
        disableLegacyEncoding: Boolean = false
    ): List<Multi>? {

        val authMap = getHeaderMap()
        val req = api.redditorMultis(
            username = username,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.map { it.data }
    }
}
