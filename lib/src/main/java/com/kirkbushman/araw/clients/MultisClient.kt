package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.MultiSubmissionsFetcher
import com.kirkbushman.araw.models.Multi
import com.kirkbushman.araw.models.MultiDescription
import com.kirkbushman.araw.models.MultiSub
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class MultisClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> Map<String, String>

) {

    fun createMultiSubmissionsFetcher(

        multi: Multi,

        sorting: SubmissionsSorting = MultiSubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = MultiSubmissionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): MultiSubmissionsFetcher {
        return createMultiSubmissionsFetcher(
            username = multi.ownerName,
            multiname = multi.name,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createMultiSubmissionsFetcher(

        username: String,
        multiname: String,

        sorting: SubmissionsSorting = MultiSubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = MultiSubmissionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

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
    fun multi(

        username: String,
        multiname: String

    ): Multi? {

        val authMap = getHeaderMap()
        val req = api.multi(
            username = username,
            multiname = multiname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    @WorkerThread
    fun myMultis(): List<Multi>? {
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
    fun redditorMultis(username: String): List<Multi>? {
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

    @WorkerThread
    fun deleteMulti(

        multi: Multi

    ): Boolean {
        return deleteMulti(
            username = multi.ownerName,
            multiname = multi.name
        )
    }

    @WorkerThread
    fun deleteMulti(

        username: String,
        multiname: String

    ): Boolean {
        val authMap = getHeaderMap()
        val req = api.deleteMulti(
            username = username,
            multiname = multiname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        return res.isSuccessful
    }

    @WorkerThread
    fun multiDescription(

        multi: Multi

    ): MultiDescription? {
        return multiDescription(
            username = multi.ownerName,
            multiname = multi.name
        )
    }

    @WorkerThread
    fun multiDescription(

        username: String,
        multiname: String

    ): MultiDescription? {
        val authMap = getHeaderMap()
        val req = api.multiDescription(
            username = username,
            multiname = multiname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    @WorkerThread
    fun setMultiDescription(

        multi: Multi,
        description: String

    ): Boolean {
        return setMultiDescription(
            username = multi.ownerName,
            multiname = multi.name,
            description = description
        )
    }

    @WorkerThread
    fun setMultiDescription(

        username: String,
        multiname: String,
        description: String

    ): Boolean {
        val authMap = getHeaderMap()
        val req = api.setMultiDescription(
            username = username,
            multiname = multiname,
            model = "{\"body_md\":\"$description\"}",
            header = authMap
        )

        val res = req.execute()
        return res.isSuccessful
    }

    @WorkerThread
    fun multiSubreddit(

        username: String,
        multiname: String,
        subname: String

    ): MultiSub? {
        val authMap = getHeaderMap()
        val req = api.multiSubreddit(
            username = username,
            multiname = multiname,
            subname = subname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun addSubredditToMulti(

        username: String,
        multiname: String,
        subname: String

    ): MultiSub? {
        val authMap = getHeaderMap()
        val req = api.addSubredditToMulti(
            username = username,
            multiname = multiname,
            subname = subname,
            model = "{\"name\":\"$subname\"}",
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun removeSubredditFromMulti(

        username: String,
        multiname: String,
        subname: String

    ): Boolean {
        val authMap = getHeaderMap()
        val req = api.removeSubredditToMulti(
            username = username,
            multiname = multiname,
            subname = subname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        return res.isSuccessful
    }
}
