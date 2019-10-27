package com.kirkbushman.araw

import com.kirkbushman.araw.clients.AccountRedditClient
import com.kirkbushman.araw.clients.ContributionRedditClient
import com.kirkbushman.araw.clients.MessagesRedditClient
import com.kirkbushman.araw.clients.SubredditRedditClient
import com.kirkbushman.araw.clients.UsersRedditClient
import com.kirkbushman.araw.fetcher.CommentsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.RedditorSearchFetcher
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.fetcher.SubmissionsSearchFetcher
import com.kirkbushman.araw.fetcher.SubredditsSearchFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.models.general.RedditorSearchSorting
import com.kirkbushman.araw.models.general.SearchSorting
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.SubredditSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.utils.Utils.getRetrofit
import com.kirkbushman.auth.models.TokenBearer

class RedditClient(private val bearer: TokenBearer, logging: Boolean) {

    private val retrofit = getRetrofit(logging)
    private val api = retrofit.create(RedditApi::class.java)

    val accountClient by lazy { AccountRedditClient(api, { currentUser }, { me -> currentUser = me }, ::getHeaderMap) }
    val contributionClient by lazy { ContributionRedditClient(api, ::getHeaderMap) }
    val messageClient by lazy { MessagesRedditClient(api, ::getHeaderMap) }
    val subredditClient by lazy { SubredditRedditClient(api, ::getHeaderMap) }
    val usersClient by lazy { UsersRedditClient(api, ::getHeaderMap) }

    private var currentUser: Me? = null
    fun getCurrentUser(): Me? = currentUser

    init {
        currentUser = accountClient.me() ?: throw IllegalStateException("Could not found logged user")
    }

    fun user(username: String): Redditor? {

        val authMap = getHeaderMap()
        val req = api.user(username = username, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun fetchRedditorSearch(

        query: String,
        show: String? = null,

        limit: Int = Fetcher.DEFAULT_LIMIT,
        sorting: RedditorSearchSorting = RedditorSearchFetcher.DEFAULT_SORTING

    ): RedditorSearchFetcher {
        return RedditorSearchFetcher(api, query, show, limit, sorting) { getHeaderMap() }
    }

    fun subreddit(subreddit: String): Subreddit? {

        val authMap = getHeaderMap()
        val req = api.subreddit(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
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

    fun subscribe(subreddit: Subreddit, skipInitialDefaults: Boolean = true): Any? {

        return subscribe(

            subredditNames = listOf(subreddit.displayName),

            action = !subreddit.isSubscriber,
            skipInitialDefaults = skipInitialDefaults
        )
    }

    fun subscribe(

        subredditIds: List<String>? = null,
        subredditNames: List<String>? = null,

        action: Boolean,

        skipInitialDefaults: Boolean = true

    ): Any? {

        val authMap = getHeaderMap()
        val req = api.subscribe(
            subredditIds = subredditIds?.joinToString(separator = ","),
            subredditNames = subredditNames?.joinToString(separator = ","),
            action = if (action) "sub" else "unsub",
            skipInitialDefaults = if (action) skipInitialDefaults else null,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun submission(submissionId: String): Submission? {

        val authMap = getHeaderMap()
        val req = api.submission(submissionId = "t3_$submissionId", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        // take the first in the listing
        return res.body()?.data?.children?.first()?.data
    }

    fun submissions(

        subreddit: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {
        return SubmissionsFetcher(

            api = api,
            subreddit = subreddit,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod

        ) { getHeaderMap() }
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
            timePeriod = timePeriod

        ) { getHeaderMap() }
    }

    fun comment(commentId: String): Comment? {

        val authMap = getHeaderMap()
        val req = api.comment(commentId = "t1_$commentId", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children?.first()?.data
    }

    fun moreChildren(moreComments: MoreComments, submission: Submission): List<CommentData>? {

        val authMap = getHeaderMap()
        val req = api.moreChildren(
            children = moreComments.children.joinToString(separator = ","),
            linkId = submission.fullname,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.json?.data?.things?.map { it.data }?.toList()
    }

    fun comments(

        submissionId: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,
        depth: Int? = null

    ): CommentsFetcher {

        return CommentsFetcher(api, submissionId, limit = limit, depth = depth) { getHeaderMap() }
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

    fun rules(subreddit: String): Array<SubredditRule>? {

        val authMap = getHeaderMap()
        val req = api.rules(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.rules
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
            timePeriod = timePeriod

        ) { getHeaderMap() }
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
            timePeriod = timePeriod

        ) { getHeaderMap() }
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
            timePeriod = timePeriod

        ) { getHeaderMap() }
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
            timePeriod = timePeriod

        ) { getHeaderMap() }
    }

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getRawAccessToken()))
    }
}
