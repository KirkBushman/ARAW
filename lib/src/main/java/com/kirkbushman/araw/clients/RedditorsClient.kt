package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.RedditorSearchFetcher
import com.kirkbushman.araw.models.ModeratedSub
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.RedditorSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod

class RedditorsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    @WorkerThread
    fun redditor(username: String, disableLegacyEncoding: Boolean = false): Redditor? {

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
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun submitted(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "submitted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun comments(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "comments",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun gilded(

        username: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return fetchContributions(

            username = username,
            where = "gilded",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun fetchContributions(

        username: String,
        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

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

    fun fetchRedditorSearch(

        query: String,
        show: String? = null,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: RedditorSearchSorting = RedditorSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = RedditorSearchFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): RedditorSearchFetcher {

        return RedditorSearchFetcher(
            api = api,
            query = query,
            show = show,
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
        val req = api.redditorModeratedSubreddits(username, authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    @WorkerThread
    fun trophies(username: String, disableLegacyEncoding: Boolean = false): List<Trophy>? {

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
