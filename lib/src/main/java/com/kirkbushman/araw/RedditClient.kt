package com.kirkbushman.araw

import com.kirkbushman.araw.clients.AccountsClient
import com.kirkbushman.araw.clients.ContributionsClient
import com.kirkbushman.araw.clients.MessagesClient
import com.kirkbushman.araw.clients.SubredditsClient
import com.kirkbushman.araw.clients.RedditorsClient
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.RedditorSearchFetcher
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.fetcher.SubmissionsSearchFetcher
import com.kirkbushman.araw.fetcher.SubredditsSearchFetcher
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.models.general.RedditorSearchSorting
import com.kirkbushman.araw.models.general.SearchSorting
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.SubredditSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.utils.Utils.getRetrofit
import com.kirkbushman.auth.models.TokenBearer

class RedditClient(private val bearer: TokenBearer, logging: Boolean) {

    private val retrofit = getRetrofit(logging)
    private val api = retrofit.create(RedditApi::class.java)

    val accountClient by lazy { AccountsClient(api, { currentUser }, { me -> currentUser = me }, ::getHeaderMap) }
    val contributionClient by lazy { ContributionsClient(api, ::getHeaderMap) }
    val messageClient by lazy { MessagesClient(api, ::getHeaderMap) }
    val subredditClient by lazy { SubredditsClient(api, ::getHeaderMap) }
    val usersClient by lazy { RedditorsClient(api, ::getHeaderMap) }

    private var currentUser: Me? = null
    fun getCurrentUser(): Me? = currentUser

    init {
        currentUser = accountClient.me() ?: throw IllegalStateException("Could not found logged user")
    }

    fun fetchRedditorSearch(

        query: String,
        show: String? = null,

        limit: Int = Fetcher.DEFAULT_LIMIT,
        sorting: RedditorSearchSorting = RedditorSearchFetcher.DEFAULT_SORTING

    ): RedditorSearchFetcher {
        return RedditorSearchFetcher(api, query, show, limit, sorting) { getHeaderMap() }
    }

    fun fetchSubredditsSearch(

        query: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,
        sorting: SubredditSearchSorting = SubredditsSearchFetcher.DEFAULT_SORTING

    ): SubredditsSearchFetcher {
        return SubredditsSearchFetcher(api, query, limit, sorting) { getHeaderMap() }
    }

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

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SearchSorting = SubmissionsSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsSearchFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsSearchFetcher {
        return SubmissionsSearchFetcher(

            api = api,
            subreddit = subreddit,
            query = query,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = ::getHeaderMap
        )
    }

    fun wiki(subreddit: String): WikiPage? {

        val authMap = getHeaderMap()
        val req = api.wiki(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun frontpage(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = ::getHeaderMap
        )
    }

    fun all(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "all",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = ::getHeaderMap
        )
    }

    fun popular(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "popular",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = ::getHeaderMap
        )
    }

    fun friends(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "friends",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = ::getHeaderMap
        )
    }

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getRawAccessToken()))
    }
}
