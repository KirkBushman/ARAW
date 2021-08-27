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

    fun overview(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun submitted(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "submitted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun comments(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "comments",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun gilded(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "gilded",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun fetchContributions(

        username: String,
        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

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

        return res.body()?.data?.trophies?.map { it.data }?.toList()
    }
}
