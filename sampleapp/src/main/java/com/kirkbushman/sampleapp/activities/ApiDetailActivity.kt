package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_api_detail.*

class ApiDetailActivity : AppCompatActivity() {

    companion object {

        private const val PARAM_API_CALL = "param_intent_api_call"

        private const val API_ME = "param_api_call_me"
        private const val API_MY_BLOCKED = "param_api_call_my_blocked"
        private const val API_MY_FRIENDS = "param_api_call_my_friends"
        private const val API_MY_KARMA = "param_api_call_my_karma"
        private const val API_MY_PREFS = "param_api_call_my_prefs"
        private const val API_MY_TROPHIES = "param_api_call_my_trophies"
        private const val API_OVERVIEW = "param_api_call_overview"
        private const val API_SUBMITTED = "param_api_call_submitted"
        private const val API_COMMENTS = "param_api_call_comments"
        private const val API_SAVED = "param_api_call_saved"
        private const val API_HIDDEN = "param_api_call_hidden"
        private const val API_UPVOTED = "param_api_call_upvoted"
        private const val API_DOWNVOTED = "param_api_call_downvoted"
        private const val API_GILDED = "param_api_call_gilded"
        private const val API_MESSAGE_INBOX = "param_api_call_inbox"
        private const val API_MESSAGE_UNREAD = "param_api_call_unread"
        private const val API_MESSAGE_SENT = "param_api_call_sent"
        private const val API_MESSAGE_MESSAGES = "param_api_call_messages"
        private const val API_MESSAGE_COMMENT_REPLIES = "param_api_call_comment_replies"
        private const val API_MESSAGE_SELF_REPLIES = "param_api_call_self_replies"
        private const val API_SUBREDDIT = "param_subreddit_call"
        private const val API_SUBREDDIT_BANNED = "param_subreddit_call_banned"
        private const val API_SUBREDDIT_MUTED = "param_subreddit_call_muted"
        private const val API_SUBREDDIT_WIKIBANNED = "param_subreddit_call_wikibanned"
        private const val API_SUBREDDIT_CONTRIBUTORS = "param_subreddit_call_contributors"
        private const val API_SUBREDDIT_WIKICONTRIBUTORS = "param_subreddit_call_wikicontributors"
        private const val API_SUBREDDIT_MODERATORS = "param_subreddit_call_moderators"
        private const val API_USER_OVERVIEW = "param_user_call_overview"
        private const val API_USER_SUBMITTED = "param_user_call_submitted"
        private const val API_USER_COMMENTS = "param_user_call_comments"
        private const val API_USER_GILDED = "param_user_call_gilded"
        private const val API_USER_TROPHIES = "param_user_call_trophies"

        fun startApiMe(context: Context) {
            start(context, API_ME)
        }

        fun startApiMyBlocked(context: Context) {
            start(context, API_MY_BLOCKED)
        }

        fun startApiMyFriends(context: Context) {
            start(context, API_MY_FRIENDS)
        }

        fun startApiMyKarma(context: Context) {
            start(context, API_MY_KARMA)
        }

        fun startApiMyPrefs(context: Context) {
            start(context, API_MY_PREFS)
        }

        fun startApiMyTrophies(context: Context) {
            start(context, API_MY_TROPHIES)
        }

        fun startApiOverview(context: Context) {
            start(context, API_OVERVIEW)
        }

        fun startApiSubmitted(context: Context) {
            start(context, API_SUBMITTED)
        }

        fun startApiComments(context: Context) {
            start(context, API_COMMENTS)
        }

        fun startApiSaved(context: Context) {
            start(context, API_SAVED)
        }

        fun startApiHidden(context: Context) {
            start(context, API_HIDDEN)
        }

        fun startApiUpvoted(context: Context) {
            start(context, API_UPVOTED)
        }

        fun startApiDownvoted(context: Context) {
            start(context, API_DOWNVOTED)
        }

        fun startApiGilded(context: Context) {
            start(context, API_GILDED)
        }

        fun startApiInbox(context: Context) {
            start(context, API_MESSAGE_INBOX)
        }

        fun startApiUnread(context: Context) {
            start(context, API_MESSAGE_UNREAD)
        }

        fun startApiSent(context: Context) {
            start(context, API_MESSAGE_SENT)
        }

        fun startApiMessages(context: Context) {
            start(context, API_MESSAGE_MESSAGES)
        }

        fun startApiCommentReplies(context: Context) {
            start(context, API_MESSAGE_COMMENT_REPLIES)
        }

        fun startApiSelfReplies(context: Context) {
            start(context, API_MESSAGE_SELF_REPLIES)
        }

        fun startApiSubreddit(context: Context) {
            start(context, API_SUBREDDIT)
        }

        fun startApiSubBanned(context: Context) {
            start(context, API_SUBREDDIT_BANNED)
        }

        fun startApiSubMuted(context: Context) {
            start(context, API_SUBREDDIT_MUTED)
        }

        fun startApiSubWikiBanned(context: Context) {
            start(context, API_SUBREDDIT_WIKIBANNED)
        }

        fun startApiSubContributors(context: Context) {
            start(context, API_SUBREDDIT_CONTRIBUTORS)
        }

        fun startApiSubWikiContributors(context: Context) {
            start(context, API_SUBREDDIT_WIKICONTRIBUTORS)
        }

        fun startApiSubModerators(context: Context) {
            start(context, API_SUBREDDIT_MODERATORS)
        }

        fun startApiUserOverview(context: Context) {
            start(context, API_USER_OVERVIEW)
        }

        fun startApiUserSubmitted(context: Context) {
            start(context, API_USER_SUBMITTED)
        }

        fun startApiUserComments(context: Context) {
            start(context, API_USER_COMMENTS)
        }

        fun startApiUserGilded(context: Context) {
            start(context, API_USER_GILDED)
        }

        fun startApiUserTrophies(context: Context) {
            start(context, API_USER_TROPHIES)
        }

        fun start(context: Context, call: String) {

            val intent = Intent(context, ApiDetailActivity::class.java)
            intent.putExtra(PARAM_API_CALL, call)

            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }
    private val apiParam by lazy { intent.getStringExtra(PARAM_API_CALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_detail)

        var result = ""
        doAsync(doWork = { result = fetchCall() }, onPost = { api_detail.text = result })
    }

    private fun fetchCall(): String {

        when (apiParam) {
            API_ME -> {
                val me = client?.accountClient?.me()
                return me.toString()
            }

            API_MY_BLOCKED -> {
                val blocked = client?.accountClient?.myBlocked()
                return blocked.toString()
            }

            API_MY_FRIENDS -> {
                val friends = client?.accountClient?.myFriends()
                return friends.toString()
            }

            API_MY_KARMA -> {
                val karma = client?.accountClient?.myKarma()
                return karma.toString()
            }

            API_MY_PREFS -> {
                val prefs = client?.accountClient?.myPrefs()
                return prefs.toString()
            }

            API_MY_TROPHIES -> {
                val trophies = client?.accountClient?.myTrophies()
                return trophies.toString()
            }

            API_OVERVIEW -> {
                val fetcher = client?.accountClient?.overview()
                val overview = fetcher?.fetchNext()
                return overview.toString()
            }

            API_SUBMITTED -> {
                val fetcher = client?.accountClient?.submitted()
                val submitted = fetcher?.fetchNext()
                return submitted.toString()
            }

            API_COMMENTS -> {
                val fetcher = client?.accountClient?.comments()
                val comments = fetcher?.fetchNext()
                return comments.toString()
            }

            API_SAVED -> {
                val fetcher = client?.accountClient?.saved()
                val saved = fetcher?.fetchNext()
                return saved.toString()
            }

            API_HIDDEN -> {
                val fetcher = client?.accountClient?.hidden()
                val hidden = fetcher?.fetchNext()
                return hidden.toString()
            }

            API_UPVOTED -> {
                val fetcher = client?.accountClient?.upvoted()
                val upvoted = fetcher?.fetchNext()
                return upvoted.toString()
            }

            API_DOWNVOTED -> {
                val fetcher = client?.accountClient?.downvoted()
                val downvoted = fetcher?.fetchNext()
                return downvoted.toString()
            }

            API_GILDED -> {
                val fetcher = client?.accountClient?.gilded()
                val gilded = fetcher?.fetchNext()
                return gilded.toString()
            }

            API_MESSAGE_INBOX -> {
                val fetcher = client?.messageClient?.inbox()
                val inbox = fetcher?.fetchNext()
                return inbox.toString()
            }

            API_MESSAGE_UNREAD -> {
                val fetcher = client?.messageClient?.unread()
                val unread = fetcher?.fetchNext()
                return unread.toString()
            }

            API_MESSAGE_MESSAGES -> {
                val fetcher = client?.messageClient?.messages()
                val messages = fetcher?.fetchNext()
                return messages.toString()
            }

            API_MESSAGE_SENT -> {
                val fetcher = client?.messageClient?.sent()
                val sent = fetcher?.fetchNext()
                return sent.toString()
            }

            API_MESSAGE_COMMENT_REPLIES -> {
                val fetcher = client?.messageClient?.commentsReplies()
                val replies = fetcher?.fetchNext()
                return replies.toString()
            }

            API_MESSAGE_SELF_REPLIES -> {
                val fetcher = client?.messageClient?.selfReplies()
                val replies = fetcher?.fetchNext()
                return replies.toString()
            }

            API_SUBREDDIT -> {
                val subName = getRandomSubredditName()
                val subreddit = client?.subredditClient?.subreddit(subName)
                return subreddit.toString()
            }

            API_SUBREDDIT_BANNED -> {
                val subName = getRandomSubredditName()
                val banned = client?.subredditClient?.subredditBanned(subName)
                return banned.toString()
            }

            API_SUBREDDIT_MUTED -> {
                val subName = getRandomSubredditName()
                val muted = client?.subredditClient?.subredditMuted(subName)
                return muted.toString()
            }

            API_SUBREDDIT_WIKIBANNED -> {
                val subName = getRandomSubredditName()
                val wikibanned = client?.subredditClient?.subredditWikiBanned(subName)
                return wikibanned.toString()
            }

            API_SUBREDDIT_CONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val contributors = client?.subredditClient?.subredditContributors(subName)
                return contributors.toString()
            }

            API_SUBREDDIT_WIKICONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val wikicontributors = client?.subredditClient?.subredditWikiContributors(subName)
                return wikicontributors.toString()
            }

            API_SUBREDDIT_MODERATORS -> {
                val subName = getRandomSubredditName()
                val moderators = client?.subredditClient?.subredditModerators(subName)
                return moderators.toString()
            }

            API_USER_OVERVIEW -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.usersClient?.overview(userName)
                val overview = fetcher?.fetchNext()
                return overview.toString()
            }

            API_USER_SUBMITTED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.usersClient?.submitted(userName)
                val submitted = fetcher?.fetchNext()
                return submitted.toString()
            }

            API_USER_COMMENTS -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.usersClient?.comments(userName)
                val comments = fetcher?.fetchNext()
                return comments.toString()
            }

            API_USER_GILDED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.usersClient?.gilded(userName)
                val gilded = fetcher?.fetchNext()
                return gilded.toString()
            }

            API_USER_TROPHIES -> {
                val userName = getRandomUserFromRandomSubreddit()
                val trophies = client?.usersClient?.trophies(userName)
                return trophies.toString()
            }
        }

        return "Api call not registered!"
    }

    private fun getRandomSubredditName(): String {

        val list = listOf(
            "AskReddit",
            "pics",
            "funny",
            "science",
            "videos",
            "news",
            "worldnews",
            "memes",
            "tifu",
            "space",
            "movies",
            "politics",
            "dataisbeautiful"
        )

        return list.random()
    }

    private fun getRandomUserFromRandomSubreddit(): String {
        val subredditName = getRandomSubredditName()
        val fetcher = client?.contributionClient?.submissions(subredditName)
        val submissions = fetcher?.fetchNext()

        return submissions?.map { it.author }?.toList()?.random() ?: ""
    }
}
