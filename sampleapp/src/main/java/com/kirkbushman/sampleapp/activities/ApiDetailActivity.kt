package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.databinding.ActivityApiDetailBinding
import com.kirkbushman.sampleapp.util.DoAsync

class ApiDetailActivity : BaseActivity() {

    companion object {

        private val subreddits = listOf(
            "AskReddit",
            "aww",
            "creepy",
            "pics",
            "funny",
            "science",
            "videos",
            "iama",
            "news",
            "television",
            "nosleep",
            "worldnews",
            "gaming",
            "diy",
            "food",
            "gifs",
            "memes",
            "tifu",
            "space",
            "movies",
            "music",
            "poll",
            "politics",
            "dataisbeautiful",
            "crosspost"
        )

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
        private const val API_SUB_SUBMISSION = "param_api_call_sub_submission"
        private const val API_SUB_MULTIREDDIT = "param_api_call_sub_multireddit"
        private const val API_SUB_COMMENT = "param_api_call_sub_comment"
        private const val API_SUB_SUBMISSIONS = "param_api_call_sub_submissions"
        private const val API_SUB_COMMENTS = "param_api_call_sub_comments"
        private const val API_SUB_TRENDING = "param_api_call_sub_trending"
        private const val API_MESSAGE_INBOX = "param_api_call_inbox"
        private const val API_MESSAGE_UNREAD = "param_api_call_unread"
        private const val API_MESSAGE_SENT = "param_api_call_sent"
        private const val API_MESSAGE_MESSAGES = "param_api_call_messages"
        private const val API_MESSAGE_COMMENT_REPLIES = "param_api_call_comment_replies"
        private const val API_MESSAGE_SELF_REPLIES = "param_api_call_self_replies"
        private const val API_MULTIS_MINE = "param_api_call_my_multis"
        private const val API_MULTIS_REDDITOR = "param_api_call_redditor_multis"
        private const val API_MULTI_SUBMISSIONS = "param_api_call_multi_submissions"
        private const val API_MULTI_GET_DESC = "param_api_call_multi_get_description"
        private const val API_MULTI_SET_DESC = "param_api_call_multi_set_description"
        private const val API_MULTI_GET_SUB = "param_api_call_multi_get_subreddit"
        private const val API_MULTI_ADD_SUB = "param_api_call_multi_add_subreddit"
        private const val API_MULTI_DEL_SUB = "param_api_call_multi_del_subreddit"
        private const val API_SUBREDDIT = "param_subreddit_call"
        private const val API_SUBREDDITS = "param_subreddits_call"
        private const val API_SUBREDDIT_BANNED = "param_subreddit_call_banned"
        private const val API_SUBREDDIT_MUTED = "param_subreddit_call_muted"
        private const val API_SUBREDDIT_RULES = "param_subreddit_call_rules"
        private const val API_SUBREDDIT_FLAIRS = "param_subreddit_call_flairs"
        private const val API_SUBREDDIT_WIKIBANNED = "param_subreddit_call_wikibanned"
        private const val API_SUBREDDIT_CONTRIBUTORS = "param_subreddit_call_contributors"
        private const val API_SUBREDDIT_WIKICONTRIBUTORS = "param_subreddit_call_wikicontributors"
        private const val API_SUBREDDIT_MODERATORS = "param_subreddit_call_moderators"
        private const val API_USER_OVERVIEW = "param_user_call_overview"
        private const val API_USER_SUBMITTED = "param_user_call_submitted"
        private const val API_USER_COMMENTS = "param_user_call_comments"
        private const val API_USER_GILDED = "param_user_call_gilded"
        private const val API_USER_TROPHIES = "param_user_call_trophies"
        private const val API_WIKI = "param_wiki_call_wiki"
        private const val API_WIKI_PAGES = "param_wiki_call_wiki_pages"
        private const val API_WIKI_REVISION = "param_wiki_call_wiki_revision"
        private const val API_WIKI_REVISIONS = "param_wiki_call_wiki_revisions"

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

        fun startApiSubSubmission(context: Context) {
            start(context, API_SUB_SUBMISSION)
        }

        fun startApiSubMultireddit(context: Context) {
            start(context, API_SUB_MULTIREDDIT)
        }

        fun startApiSubComment(context: Context) {
            start(context, API_SUB_COMMENT)
        }

        fun startApiSubSubmissions(context: Context) {
            start(context, API_SUB_SUBMISSIONS)
        }

        fun startApiSubComments(context: Context) {
            start(context, API_SUB_COMMENTS)
        }

        fun startApiTrending(context: Context) {
            start(context, API_SUB_TRENDING)
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

        fun startApiMyMultis(context: Context) {
            start(context, API_MULTIS_MINE)
        }

        fun startApiRedditorMultis(context: Context) {
            start(context, API_MULTIS_REDDITOR)
        }

        fun startApiMultiSubs(context: Context) {
            start(context, API_MULTI_SUBMISSIONS)
        }

        fun startApiMultiGetDesc(context: Context) {
            start(context, API_MULTI_GET_DESC)
        }

        fun startApiMultiSetDesc(context: Context) {
            start(context, API_MULTI_SET_DESC)
        }

        fun startApiMultiGetSub(context: Context) {
            start(context, API_MULTI_GET_SUB)
        }

        fun startApiMultiAddSub(context: Context) {
            start(context, API_MULTI_ADD_SUB)
        }

        fun startApiMultiDelSub(context: Context) {
            start(context, API_MULTI_DEL_SUB)
        }

        fun startApiSubreddit(context: Context) {
            start(context, API_SUBREDDIT)
        }

        fun startApiSubreddits(context: Context) {
            start(context, API_SUBREDDITS)
        }

        fun startApiSubBanned(context: Context) {
            start(context, API_SUBREDDIT_BANNED)
        }

        fun startApiSubMuted(context: Context) {
            start(context, API_SUBREDDIT_MUTED)
        }

        fun startApiSubRules(context: Context) {
            start(context, API_SUBREDDIT_RULES)
        }

        fun startApiSubFlairs(context: Context) {
            start(context, API_SUBREDDIT_FLAIRS)
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

        fun startApiWiki(context: Context) {
            start(context, API_WIKI)
        }

        fun startApiWikiPages(context: Context) {
            start(context, API_WIKI_PAGES)
        }

        fun startApiWikiRevision(context: Context) {
            start(context, API_WIKI_REVISION)
        }

        fun startApiWikiRevisions(context: Context) {
            start(context, API_WIKI_REVISIONS)
        }

        fun start(context: Context, call: String) {

            val intent = Intent(context, ApiDetailActivity::class.java)
            intent.putExtra(PARAM_API_CALL, call)

            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }
    private val apiParam by lazy { intent.getStringExtra(PARAM_API_CALL) }

    private lateinit var binding: ActivityApiDetailBinding

    private val disableLegacyEncoding by lazy {

        val prefs = SettingsActivity.getPrefs(this)
        prefs.getDisableLegacyEncoding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var result = ""
        DoAsync(
            doWork = { result = fetchCall() },
            onPost = { binding.apiDetail.text = result }
        )
    }

    private fun fetchCall(): String {

        when (apiParam) {
            API_ME -> {
                val me = client?.accountsClient?.me()
                return me.toString()
            }

            API_MY_BLOCKED -> {
                val blocked = client?.accountsClient?.myBlocked()
                return blocked.toString()
            }

            API_MY_FRIENDS -> {
                val friends = client?.accountsClient?.myFriends()
                return friends.toString()
            }

            API_MY_KARMA -> {
                val karma = client?.accountsClient?.myKarma()
                return karma.toString()
            }

            API_MY_PREFS -> {
                val prefs = client?.accountsClient?.myPrefs()
                return prefs.toString()
            }

            API_MY_TROPHIES -> {
                val trophies = client?.accountsClient?.myTrophies()
                return trophies.toString()
            }

            API_OVERVIEW -> {
                val fetcher = client?.accountsClient?.overview(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val overview = fetcher?.fetchNext()
                return overview.toString()
            }

            API_SUBMITTED -> {
                val fetcher = client?.accountsClient?.submitted(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val submitted = fetcher?.fetchNext()
                return submitted.toString()
            }

            API_COMMENTS -> {
                val fetcher = client?.accountsClient?.comments(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val comments = fetcher?.fetchNext()
                return comments.toString()
            }

            API_SAVED -> {
                val fetcher = client?.accountsClient?.saved(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val saved = fetcher?.fetchNext()
                return saved.toString()
            }

            API_HIDDEN -> {
                val fetcher = client?.accountsClient?.hidden(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val hidden = fetcher?.fetchNext()
                return hidden.toString()
            }

            API_UPVOTED -> {
                val fetcher = client?.accountsClient?.upvoted(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val upvoted = fetcher?.fetchNext()
                return upvoted.toString()
            }

            API_DOWNVOTED -> {
                val fetcher = client?.accountsClient?.downvoted(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val downvoted = fetcher?.fetchNext()
                return downvoted.toString()
            }

            API_GILDED -> {
                val fetcher = client?.accountsClient?.gilded(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val gilded = fetcher?.fetchNext()
                return gilded.toString()
            }

            API_SUB_SUBMISSION -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val submission = client?.contributionsClient?.submission(
                    submissionId,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return submission.toString()
            }

            API_SUB_COMMENT -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val fetcher = client?.contributionsClient?.comments(
                    submissionId,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val comments = fetcher?.fetchNext()
                return comments?.random().toString()
            }

            API_SUB_SUBMISSIONS -> {
                val subreddit = getRandomSubredditName()
                val fetcher = client?.contributionsClient?.submissions(
                    subreddit,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val submissions = fetcher?.fetchNext()
                return submissions.toString()
            }

            API_SUB_COMMENTS -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val fetcher = client?.contributionsClient?.comments(
                    submissionId,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val comments = fetcher?.fetchNext()
                return comments.toString()
            }

            API_SUB_MULTIREDDIT -> {
                val subreddits = getRandomSubredditNames()
                val fetcher = client?.contributionsClient?.multiredditSubmissions(*subreddits, limit = 100)
                val submissions = fetcher?.fetchNext()
                return submissions.toString()
            }

            API_SUB_TRENDING -> {
                val trending = client?.contributionsClient?.trendingSubreddits()
                return trending.toString()
            }

            API_MESSAGE_INBOX -> {
                val fetcher = client?.messagesClient?.inbox(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val inbox = fetcher?.fetchNext()
                return inbox.toString()
            }

            API_MESSAGE_UNREAD -> {
                val fetcher = client?.messagesClient?.unread(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val unread = fetcher?.fetchNext()
                return unread.toString()
            }

            API_MESSAGE_MESSAGES -> {
                val fetcher = client?.messagesClient?.messages(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val messages = fetcher?.fetchNext()
                return messages.toString()
            }

            API_MESSAGE_SENT -> {
                val fetcher = client?.messagesClient?.sent(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val sent = fetcher?.fetchNext()
                return sent.toString()
            }

            API_MESSAGE_COMMENT_REPLIES -> {
                val fetcher = client?.messagesClient?.commentsReplies(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val replies = fetcher?.fetchNext()
                return replies.toString()
            }

            API_MESSAGE_SELF_REPLIES -> {
                val fetcher = client?.messagesClient?.selfReplies(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val replies = fetcher?.fetchNext()
                return replies.toString()
            }

            API_MULTIS_MINE -> {
                val multis = client?.multisClient?.myMultis(
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return multis.toString()
            }

            API_MULTIS_REDDITOR -> {
                val userName = "Kirk-Bushman" // getRandomUserFromRandomSubreddit()
                val multis = client?.multisClient?.redditorMultis(
                    username = userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return multis.toString()
            }

            API_MULTI_SUBMISSIONS -> {
                val fetcher = client
                    ?.multisClient
                    ?.multiSubmissions("Kirk-Bushman", "karmafarming")

                val submissions = fetcher?.fetchNext()
                return submissions.toString()
            }

            API_MULTI_GET_DESC -> {

                val desc = client
                    ?.multisClient
                    ?.getMultiDescription("Kirk-Bushman", "karmafarming")

                return desc.toString()
            }

            API_MULTI_SET_DESC -> {

            }

            API_MULTI_GET_SUB -> {

                val subreddit = client
                    ?.multisClient
                    ?.getMultiSubreddit("Kirk-Bushman", "karmafarming", "Karma_Exchange")

                return subreddit.toString()
            }

            API_MULTI_ADD_SUB -> {

                val response = client
                    ?.multisClient
                    ?.addSubredditToMulti("Kirk-Bushman", "shush", "pics")

                return response.toString()
            }

            API_MULTI_DEL_SUB -> {

            }

            API_SUBREDDIT -> {
                val subName = getRandomSubredditName()
                val subreddit = client?.subredditsClient?.subreddit(
                    subreddit = subName,
                    disableLegacyEncoding = disableLegacyEncoding
                )

                return subreddit.toString()
            }

            API_SUBREDDITS -> {
                val subIds = getRandomSubredditIds()
                val subreddits = client?.subredditsClient?.subreddits(
                    disableLegacyEncoding = disableLegacyEncoding,
                    ids = subIds.toTypedArray()
                )

                return subreddits.toString()
            }

            API_SUBREDDIT_BANNED -> {
                val subName = getRandomSubredditName()
                val banned = client?.subredditsClient?.subredditBanned(subName)
                return banned.toString()
            }

            API_SUBREDDIT_MUTED -> {
                val subName = getRandomSubredditName()
                val muted = client?.subredditsClient?.subredditMuted(subName)
                return muted.toString()
            }

            API_SUBREDDIT_RULES -> {
                val subName = getRandomSubredditName()
                val rules = client?.subredditsClient?.rules(subName)
                return rules?.contentToString() ?: "null"
            }

            API_SUBREDDIT_FLAIRS -> {
                val subName = getRandomSubredditName()
                val flairs = client?.subredditsClient?.subredditFlairs(subName)
                return flairs.toString()
            }

            API_SUBREDDIT_WIKIBANNED -> {
                val subName = getRandomSubredditName()
                val wikibanned = client?.subredditsClient?.subredditWikiBanned(subName)
                return wikibanned.toString()
            }

            API_SUBREDDIT_CONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val contributors = client?.subredditsClient?.subredditContributors(subName)
                return contributors.toString()
            }

            API_SUBREDDIT_WIKICONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val wikicontributors = client?.subredditsClient?.subredditWikiContributors(subName)
                return wikicontributors.toString()
            }

            API_SUBREDDIT_MODERATORS -> {
                val subName = getRandomSubredditName()
                val moderators = client?.subredditsClient?.subredditModerators(subName)
                return moderators.toString()
            }

            API_USER_OVERVIEW -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.redditorsClient?.overview(
                    userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val overview = fetcher?.fetchNext()
                return overview.toString()
            }

            API_USER_SUBMITTED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.redditorsClient?.submitted(
                    userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val submitted = fetcher?.fetchNext()
                return submitted.toString()
            }

            API_USER_COMMENTS -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.redditorsClient?.comments(
                    userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val comments = fetcher?.fetchNext()
                return comments.toString()
            }

            API_USER_GILDED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client?.redditorsClient?.gilded(
                    userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                val gilded = fetcher?.fetchNext()
                return gilded.toString()
            }

            API_USER_TROPHIES -> {
                val userName = getRandomUserFromRandomSubreddit()
                val trophies = client?.redditorsClient?.trophies(
                    userName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return trophies.toString()
            }

            API_WIKI -> {
                val subredditName = getRandomSubredditName()
                val wiki = client?.wikisClient?.wiki(
                    subreddit = subredditName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return wiki.toString()
            }

            API_WIKI_PAGES -> {
                val subredditName = getRandomSubredditName()
                val wikiPages = client?.wikisClient?.wikiPages(
                    subreddit = subredditName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return wikiPages.toString()
            }

            API_WIKI_REVISION -> {
                val subredditName = getRandomSubredditName()
                val wikiRevisions = client?.wikisClient?.wikiRevision(
                    subreddit = subredditName,
                    page = "index",
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return wikiRevisions.toString()
            }

            API_WIKI_REVISIONS -> {
                val subredditName = getRandomSubredditName()
                val wikiRevisions = client?.wikisClient?.wikiRevisions(
                    subreddit = subredditName,
                    disableLegacyEncoding = disableLegacyEncoding
                )
                return wikiRevisions.toString()
            }
        }

        return "Api call not registered!"
    }

    private fun getRandomSubredditName(): String {
        return subreddits.random()
    }

    private fun getRandomSubredditNames(): Array<String> {
        return subreddits.shuffled().take(4).toTypedArray()
    }

    private fun getRandomSubredditIds(): List<String> {

        return listOf(

            // centuryclub
            // testing private subreddit
            "2u3fa",
            // announcements
            "2r0ij",
            // art
            "2qh7a",
            // askReddit
            "2qh1i",
            // askScience
            "2qm4e",
            // aww
            "2qh1o",
            // blog
            "2qh49",
            // books
            "2qh4i",
            // creepy
            "2raed",
            // dataIsBeautiful
            "2tk95",
            // diy
            "2qh7d",
            // documentaries
            "2qhlh",
            // earthPorn
            "2sbq3",
            // explainLikeImFive
            "2sokd",
            // food
            "2qh55",
            // funny
            "2qh33",
            // futurology
            "2t7no",
            // getMotivated
            "2rmfx",
            // gifs
            "2qt55",
            // history
            "2qh53",
            // iAma
            "2qzb6",
            // internetIsBeautiful
            "2ul7u",
            // jokes
            "2qh72",
            // lifeProTips
            "2s5oq",
            // listenToThis
            "2qxzy",
            // mildlyInteresting
            "2ti4h",
            // movies
            "2qh3s",
            // music
            "2qh1u",
            // news
            "2qh3l",
            // noSleep
            "2rm4d",
            // notTheOnion
            "2qnts",
            // oldSchoolCool
            "2tycb",
            // personalFinance
            "2qstm",
            // philosophy
            "2qh5b",
            // photoshopBattles
            "2tecy",
            // pics
            "2qh0u",
            // science
            "mouw",
            // showerThoughts
            "2szyo",
            // space
            "2qh87",
            // soccer
            "2qi58",
            // television
            "2qh6e",
            // tifu
            "2to41",
            // todayILearned
            "2qqjc",
            // twoXChromosomes
            "2r2jt",
            // upliftingNews
            "2u3ta",
            // videos
            "2qh1e",
            // worldnews
            "2qh13",
            // writingPrompts
            "2s3nb"
        )
    }

    private fun getRandomSubmissionIdFromRandomSubreddit(): String {
        val subredditName = getRandomSubredditName()
        val fetcher = client?.contributionsClient?.submissions(
            subredditName,
            disableLegacyEncoding = disableLegacyEncoding
        )
        val submissions = fetcher?.fetchNext()

        return submissions?.map { it.id }?.random() ?: ""
    }

    private fun getRandomUserFromRandomSubreddit(): String {
        val subredditName = getRandomSubredditName()
        val fetcher = client?.contributionsClient?.submissions(
            subredditName,
            disableLegacyEncoding = disableLegacyEncoding
        )
        val submissions = fetcher?.fetchNext()

        return submissions?.map { it.author }?.toList()?.random() ?: ""
    }
}
