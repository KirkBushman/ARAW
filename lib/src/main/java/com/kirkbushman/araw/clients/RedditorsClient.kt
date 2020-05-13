package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.RedditorSearchFetcher
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.RedditorSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod

class RedditorsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

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
        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            username = username,
            where = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun submitted(

        username: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            username = username,
            where = "submitted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun comments(

        username: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            username = username,
            where = "comments",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun gilded(

        username: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            username = username,
            where = "gilded",
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

        limit: Int = Fetcher.DEFAULT_LIMIT,

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
