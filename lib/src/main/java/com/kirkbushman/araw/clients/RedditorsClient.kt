package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.models.ModeratedSub
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class RedditorsClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> Map<String, String>

) {

    @WorkerThread
    fun redditor(username: String): RedditorData? {

        val authMap = getHeaderMap()
        val req = api.redditor(
            username = username,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun createOverviewContributionsFetcher(

        username: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {

        return createContributionsFetcher(
            username = username,
            where = ContributionsFetcher.USER_CONTRIB_OVERVIEW,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createSubmittedContributionsFetcher(

        username: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {

        return createContributionsFetcher(
            username = username,
            where = ContributionsFetcher.USER_CONTRIB_SUBMITTED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createCommentsContributionsFetcher(

        username: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {

        return createContributionsFetcher(
            username = username,
            where = ContributionsFetcher.USER_CONTRIB_COMMENTS,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createGildedContributionsFetcher(

        username: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {

        return createContributionsFetcher(
            username = username,
            where = ContributionsFetcher.USER_CONTRIB_GILDED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createContributionsFetcher(

        username: String,
        where: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {

        return ContributionsFetcher(
            api = api,
            username = username,
            where = where,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    @WorkerThread
    fun moderatedSubreddits(username: String): List<ModeratedSub>? {

        val authMap = getHeaderMap()
        val req = api.redditorModeratedSubreddits(
            username = username,
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
    fun trophies(username: String): List<Trophy>? {

        val authMap = getHeaderMap()
        val req = api.redditorTrophies(
            username = username,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.trophies?.map { it.data }
    }
}
