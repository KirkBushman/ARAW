package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.RedditorSearchFetcher
import com.kirkbushman.araw.fetcher.SubmissionsSearchFetcher
import com.kirkbushman.araw.fetcher.SubredditsSearchFetcher
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.enums.RedditorSearchSorting
import com.kirkbushman.araw.models.enums.SearchSorting
import com.kirkbushman.araw.models.enums.SubredditSearchSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class SearchClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> Map<String, String>

) {

    fun searchSubreddits(
        query: String,
        exact: Boolean? = null,
        includeOver18: Boolean? = null,
        includeUnadvertisable: Boolean? = null
    ): SubredditSearchResult? {

        val authMap = getHeaderMap()
        val req = api.searchSubreddits(
            query = query,
            exact = exact,
            includeOver18 = includeOver18,
            includeUnadvertisable = includeUnadvertisable,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun submissionsSearch(

        subreddit: String?,
        query: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SearchSorting = SubmissionsSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsSearchFetcher.DEFAULT_TIMEPERIOD,

        showAll: Boolean = false,
        restrictToSubreddit: Boolean = false

    ): SubmissionsSearchFetcher {

        return SubmissionsSearchFetcher(
            api = api,
            subreddit = subreddit,
            query = query,
            showAll = showAll,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            restrictToSubreddit = restrictToSubreddit,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun fetchSubredditsSearch(

        query: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubredditSearchSorting = SubredditsSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubredditsSearchFetcher.DEFAULT_TIMEPERIOD,

        showAll: Boolean = false

    ): SubredditsSearchFetcher {

        return SubredditsSearchFetcher(
            api = api,
            query = query,
            showAll = showAll,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun fetchRedditorSearch(

        query: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: RedditorSearchSorting = RedditorSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = RedditorSearchFetcher.DEFAULT_TIMEPERIOD,

        showAll: Boolean = false

    ): RedditorSearchFetcher {

        return RedditorSearchFetcher(
            api = api,
            query = query,
            showAll = showAll,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }
}
