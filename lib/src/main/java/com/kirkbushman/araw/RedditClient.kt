package com.kirkbushman.araw

import com.kirkbushman.araw.fetcher.CommentFetcher
import com.kirkbushman.araw.fetcher.ContributionFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.InboxFetcher
import com.kirkbushman.araw.fetcher.SubmissionFetcher
import com.kirkbushman.araw.fetcher.SubredditFetcher
import com.kirkbushman.araw.models.Account
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.utils.Utils.getRetrofit
import com.kirkbushman.auth.models.TokenBearer

class RedditClient(private val bearer: TokenBearer, logging: Boolean) {

    private val retrofit = getRetrofit(logging)
    private val api = retrofit.create(RedditApi::class.java)

    val messages by lazy { MessagesHandler(api, ::getHeaderMap) }
    val account by lazy { AccountHandler(api, ::getHeaderMap) }
    val selfAccount by lazy { SelfAccountHandler(api, { me() ?: throw IllegalStateException("Could not found logged user") }, ::getHeaderMap) }

    fun me(): Me? {

        val authMap = getHeaderMap()
        val req = api.me(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
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

    fun subreddit(subreddit: String): Subreddit? {

        val authMap = getHeaderMap()
        val req = api.subreddit(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
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

    fun submissions(subreddit: String, limit: Int = Fetcher.DEFAULT_LIMIT): SubmissionFetcher {
        return SubmissionFetcher(api, subreddit, limit = limit) { getHeaderMap() }
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

    fun comments(

        submissionId: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,
        depth: Int? = null

    ): CommentFetcher {

        return CommentFetcher(api, submissionId, limit = limit, depth = depth) { getHeaderMap() }
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

    fun accountHandler(account: Account): UserAccountHandler {
        return UserAccountHandler(api, account, ::getHeaderMap)
    }

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getRawAccessToken()))
    }

    class MessagesHandler(

        private val api: RedditApi,
        private inline val getHeaderMap: () -> HashMap<String, String>

    ) {

        fun inbox(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "inbox", limit) { getHeaderMap() }
        }

        fun unread(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "unread", limit) { getHeaderMap() }
        }

        fun messages(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "messages", limit) { getHeaderMap() }
        }

        fun sent(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "sent", limit) { getHeaderMap() }
        }

        fun commentsReplies(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "comments", limit) { getHeaderMap() }
        }

        fun selfReplies(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "selfreply", limit) { getHeaderMap() }
        }

        fun mentions(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
            return InboxFetcher(api, "mentions", limit) { getHeaderMap() }
        }
    }

    open class AccountHandler(

        private val api: RedditApi,
        private inline val getHeaderMap: () -> HashMap<String, String>

    ) {

        fun overview(username: String, limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, username, "", limit) { getHeaderMap() }
        }

        fun submitted(username: String, limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, username, "submitted", limit) { getHeaderMap() }
        }

        fun comments(username: String, limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, username, "comments", limit) { getHeaderMap() }
        }

        fun gilded(username: String, limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, username, "gilded", limit) { getHeaderMap() }
        }

        fun trophies(username: String): List<Trophy>? {

            val authMap = getHeaderMap()
            val req = api.userTrophies(username = username, header = authMap)
            val res = req.execute()

            if (!res.isSuccessful) {
                return null
            }

            return res.body()?.data?.trophies?.map { it.data }?.toList()
        }
    }

    class UserAccountHandler(

        api: RedditApi,
        private val user: Account,
        getHeaderMap: () -> HashMap<String, String>

    ) : AccountHandler(api, getHeaderMap) {

        fun overview(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return super.overview(user.name, limit)
        }

        fun submitted(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return super.submitted(user.name, limit)
        }

        fun comments(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return super.comments(user.name, limit)
        }

        fun gilded(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return super.gilded(user.name, limit)
        }

        fun trophies(): List<Trophy>? {
            return super.trophies(user.name)
        }
    }

    class SelfAccountHandler(

        private val api: RedditApi,
        private inline val getSelfAccount: () -> Me,
        private inline val getHeaderMap: () -> HashMap<String, String>

    ) {

        private var currentUser: String? = null

        fun overview(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "", limit) { getHeaderMap() }
        }

        fun submitted(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "submitted", limit) { getHeaderMap() }
        }

        fun comments(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "comments", limit) { getHeaderMap() }
        }

        fun saved(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "saved", limit) { getHeaderMap() }
        }

        fun hidden(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "hidden", limit) { getHeaderMap() }
        }

        fun upvoted(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "upvoted", limit) { getHeaderMap() }
        }

        fun downvoted(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "downvoted", limit) { getHeaderMap() }
        }

        fun gilded(limit: Int = Fetcher.DEFAULT_LIMIT): ContributionFetcher {
            return ContributionFetcher(api, getUserCached(), "gilded", limit) { getHeaderMap() }
        }

        fun subscribedSubreddits(limit: Int = Fetcher.DEFAULT_LIMIT): SubredditFetcher {
            return SubredditFetcher(api, "subscriber", limit) { getHeaderMap() }
        }

        fun trophies(): List<Trophy>? {

            val authMap = getHeaderMap()
            val req = api.selfUserTrophies(header = authMap)
            val res = req.execute()

            if (!res.isSuccessful) {
                return null
            }

            return res.body()?.data?.trophies?.map { it.data }?.toList()
        }

        private fun getUserCached(): String {

            return if (currentUser != null) {
                currentUser!!
            } else {
                currentUser = getSelfAccount().name
                currentUser!!
            }
        }
    }
}