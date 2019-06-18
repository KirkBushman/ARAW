package com.kirkbushman.araw

import com.kirkbushman.araw.fetcher.CommentFetcher
import com.kirkbushman.araw.fetcher.ContributionFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.InboxFetcher
import com.kirkbushman.araw.fetcher.SubmissionFetcher
import com.kirkbushman.araw.fetcher.SubredditFetcher
import com.kirkbushman.araw.http.EnvelopedComment
import com.kirkbushman.araw.http.EnvelopedCommentData
import com.kirkbushman.araw.http.EnvelopedContribution
import com.kirkbushman.araw.http.EnvelopedData
import com.kirkbushman.araw.http.EnvelopedMessage
import com.kirkbushman.araw.http.EnvelopedMoreComment
import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.utils.NullRepliesInterceptor
import com.kirkbushman.auth.models.TokenBearer
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RedditClient(private val bearer: TokenBearer) {

    companion object {

        private const val BASE_URL = "https://oauth.reddit.com"

        private val retrofit = getRetrofit(true)
        private val api = retrofit.create(RedditApi::class.java)

        private fun getRetrofit(logging: Boolean): Retrofit {

            val moshi = Moshi.Builder()
                .add(
                    PolymorphicJsonAdapterFactory.of(EnvelopedData::class.java, "kind")
                        .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                        .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
                        .withSubtype(EnvelopedMessage::class.java, EnvelopeKind.Message.value)
                        .withSubtype(EnvelopedRedditor::class.java, EnvelopeKind.Account.value)
                        .withSubtype(EnvelopedSubmission::class.java, EnvelopeKind.Link.value)
                        .withSubtype(EnvelopedSubreddit::class.java, EnvelopeKind.Subreddit.value)
                )
                .add(
                    PolymorphicJsonAdapterFactory.of(EnvelopedContribution::class.java, "kind")
                        .withSubtype(EnvelopedSubmission::class.java, EnvelopeKind.Link.value)
                        .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                        .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
                )
                .add(
                    PolymorphicJsonAdapterFactory.of(EnvelopedCommentData::class.java, "kind")
                        .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                        .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
                )
                .build()

            val moshiFactory = MoshiConverterFactory.create(moshi)
            val nullRepliesInterceptor = NullRepliesInterceptor

            val httpClient = if (logging) {

                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient
                    .Builder()
                    .addInterceptor(nullRepliesInterceptor)
                    .addInterceptor(logger)
                    .build()
            } else {

                OkHttpClient
                    .Builder()
                    .addInterceptor(nullRepliesInterceptor)
                    .build()
            }

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(moshiFactory)
                .client(httpClient)
                .build()
        }
    }

    val messages by lazy { MessagesHandler(::getHeaderMap) }
    val account by lazy { AccountHandler(::getHeaderMap) }
    val selfAccount by lazy { SelfAccountHandler({ me() ?: throw IllegalStateException("Could not found logged user") }, ::getHeaderMap) }

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

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getRawAccessToken()))
    }

    class MessagesHandler(private inline val getHeaderMap: () -> HashMap<String, String>) {

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

    class AccountHandler(private inline val getHeaderMap: () -> HashMap<String, String>) {

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
    }

    class SelfAccountHandler(

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