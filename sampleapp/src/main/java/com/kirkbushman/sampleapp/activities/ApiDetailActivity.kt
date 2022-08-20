package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.databinding.ActivityApiDetailBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiDetailActivity : BaseActivity() {

    companion object {

        private const val TAKE_SUB_NUM = 4

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
        private const val API_MULTI = "param_api_call_multi"
        private const val API_MULTIS_MINE = "param_api_call_my_multis"
        private const val API_MULTIS_REDDITOR = "param_api_call_redditor_multis"
        private const val API_MULTI_SUBMISSIONS = "param_api_call_multi_submissions"
        private const val API_MULTI_DEL = "param_api_call_del_multi"
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

        fun startApiMulti(context: Context) {
            start(context, API_MULTI)
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

        fun startApiDelMulti(context: Context) {
            start(context, API_MULTI_DEL)
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

    @Inject
    lateinit var client: RedditClient

    private val apiParam by lazy { intent.getStringExtra(PARAM_API_CALL) }

    private lateinit var binding: ActivityApiDetailBinding

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

        return when (apiParam) {
            API_ME -> {
                val me = client.accountsClient.me()
                me.toString()
            }

            API_MY_BLOCKED -> {
                val blocked = client.accountsClient.myBlocked()
                blocked.toString()
            }

            API_MY_FRIENDS -> {
                val friends = client.accountsClient.myFriends()
                friends.toString()
            }

            API_MY_KARMA -> {
                val karma = client.accountsClient.myKarma()
                karma.toString()
            }

            API_MY_PREFS -> {
                val prefs = client.accountsClient.myPrefs()
                prefs.toString()
            }

            API_MY_TROPHIES -> {
                val trophies = client.accountsClient.myTrophies()
                trophies.toString()
            }

            API_OVERVIEW -> {
                val fetcher = client.accountsClient.createOverviewContributionsFetcher()
                val overview = fetcher.fetchNext()
                overview.toString()
            }

            API_SUBMITTED -> {
                val fetcher = client.accountsClient.createSubmittedContributionsFetcher()
                val submitted = fetcher.fetchNext()
                submitted.toString()
            }

            API_COMMENTS -> {
                val fetcher = client.accountsClient.createCommentsContributionsFetcher()
                val comments = fetcher.fetchNext()
                comments.toString()
            }

            API_SAVED -> {
                val fetcher = client.accountsClient.createSavedContributionsFetcher()
                val saved = fetcher.fetchNext()
                saved.toString()
            }

            API_HIDDEN -> {
                val fetcher = client.accountsClient.createHiddenContributionsFetcher()
                val hidden = fetcher.fetchNext()
                hidden.toString()
            }

            API_UPVOTED -> {
                val fetcher = client.accountsClient.createUpvotedContributionsFetcher()
                val upvoted = fetcher.fetchNext()
                upvoted.toString()
            }

            API_DOWNVOTED -> {
                val fetcher = client.accountsClient.createDownvotedContributionsFetcher()
                val downvoted = fetcher.fetchNext()
                downvoted.toString()
            }

            API_GILDED -> {
                val fetcher = client.accountsClient.createGildedContributionsFetcher()
                val gilded = fetcher.fetchNext()
                gilded.toString()
            }

            API_SUB_SUBMISSION -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val submission = client.contributionsClient.submission(submissionId)
                submission.toString()
            }

            API_SUB_COMMENT -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val fetcher = client.contributionsClient.createCommentsFetcher(submissionId)
                val comments = fetcher.fetchNext()
                comments?.random().toString()
            }

            API_SUB_SUBMISSIONS -> {
                val subreddit = getRandomSubredditName()
                val fetcher = client.contributionsClient.createSubmissionsFetcher(subreddit)
                val submissions = fetcher.fetchNext()
                submissions.toString()
            }

            API_SUB_COMMENTS -> {
                val submissionId = getRandomSubmissionIdFromRandomSubreddit()
                val fetcher = client.contributionsClient.createCommentsFetcher(submissionId)
                val comments = fetcher.fetchNext()
                comments.toString()
            }

            API_SUB_MULTIREDDIT -> {
                val subreddits = getRandomSubredditNames()
                val fetcher = client
                    .contributionsClient
                    .createSubmissionsFetcher(*subreddits, limit = 100)

                val submissions = fetcher.fetchNext()
                submissions.toString()
            }

            API_SUB_TRENDING -> {
                val trending = client.contributionsClient.trendingSubreddits()
                trending.toString()
            }

            API_MESSAGE_INBOX -> {
                val fetcher = client.messagesClient.createOverviewInboxFetcher()
                val inbox = fetcher.fetchNext()
                inbox.toString()
            }

            API_MESSAGE_UNREAD -> {
                val fetcher = client.messagesClient.createUnreadInboxFetcher()
                val unread = fetcher.fetchNext()
                unread.toString()
            }

            API_MESSAGE_MESSAGES -> {
                val fetcher = client.messagesClient.createMessagesInboxFetcher()
                val messages = fetcher.fetchNext()
                messages.toString()
            }

            API_MESSAGE_SENT -> {
                val fetcher = client.messagesClient.createSentInboxFetcher()
                val sent = fetcher.fetchNext()
                sent.toString()
            }

            API_MESSAGE_COMMENT_REPLIES -> {
                val fetcher = client.messagesClient.createCommentsRepliesInboxFetcher()
                val replies = fetcher.fetchNext()
                replies.toString()
            }

            API_MESSAGE_SELF_REPLIES -> {
                val fetcher = client.messagesClient.createSelfRepliesInboxFetcher()
                val replies = fetcher.fetchNext()
                replies.toString()
            }

            API_MULTI -> {
                val multi = client.multisClient.multi(
                    username = "Kirk-Bushman",
                    multiname = "karmafarming"
                )

                multi.toString()
            }

            API_MULTIS_MINE -> {
                val multis = client.multisClient.myMultis()
                multis.toString()
            }

            API_MULTIS_REDDITOR -> {
                val userName = "Kirk-Bushman" // getRandomUserFromRandomSubreddit()
                val multis = client.multisClient.redditorMultis(userName)
                multis.toString()
            }

            API_MULTI_SUBMISSIONS -> {
                val fetcher = client
                    .multisClient
                    .createMultiSubmissionsFetcher("Kirk-Bushman", "karmafarming")

                val submissions = fetcher.fetchNext()
                submissions.toString()
            }

            API_MULTI_DEL -> {

                val response = client
                    .multisClient
                    .deleteMulti("Kirk-Bushman", "shush")

                response.toString()
            }

            API_MULTI_GET_DESC -> {

                val desc = client
                    .multisClient
                    .multiDescription("Kirk-Bushman", "karmafarming")

                desc.toString()
            }

            API_MULTI_SET_DESC -> {

                val response = client
                    .multisClient
                    .setMultiDescription("Kirk-Bushman", "karmafarming", "test description 1")

                response.toString()
            }

            API_MULTI_GET_SUB -> {

                val subreddit = client
                    .multisClient
                    .multiSubreddit("Kirk-Bushman", "karmafarming", "Karma_Exchange")

                subreddit.toString()
            }

            API_MULTI_ADD_SUB -> {

                val response = client
                    .multisClient
                    .addSubredditToMulti("Kirk-Bushman", "shush", "pics")

                response.toString()
            }

            API_MULTI_DEL_SUB -> {

                val response = client
                    .multisClient
                    .removeSubredditFromMulti("Kirk-Bushman", "shush", "pics")

                response.toString()
            }

            API_SUBREDDIT -> {
                val subName = getRandomSubredditName()
                val subreddit = client.subredditsClient.subreddit(subName)

                subreddit.toString()
            }

            API_SUBREDDITS -> {
                val subIds = getRandomSubredditIds()
                val subreddits = client.subredditsClient.subreddits(ids = subIds.toTypedArray())

                subreddits.toString()
            }

            API_SUBREDDIT_BANNED -> {
                val subName = getRandomSubredditName()
                val banned = client.subredditsClient.subredditBanned(subName)
                banned.toString()
            }

            API_SUBREDDIT_MUTED -> {
                val subName = getRandomSubredditName()
                val muted = client.subredditsClient.subredditMuted(subName)
                muted.toString()
            }

            API_SUBREDDIT_RULES -> {
                val subName = getRandomSubredditName()
                val rules = client.subredditsClient.rules(subName)
                rules.contentToString()
            }

            API_SUBREDDIT_FLAIRS -> {
                val subName = getRandomSubredditName()
                val flairs = client.subredditsClient.subredditFlairs(subName)
                flairs.toString()
            }

            API_SUBREDDIT_WIKIBANNED -> {
                val subName = getRandomSubredditName()
                val wikibanned = client.subredditsClient.subredditWikiBanned(subName)
                wikibanned.toString()
            }

            API_SUBREDDIT_CONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val contributors = client.subredditsClient.subredditContributors(subName)
                contributors.toString()
            }

            API_SUBREDDIT_WIKICONTRIBUTORS -> {
                val subName = getRandomSubredditName()
                val wikicontributors = client.subredditsClient.subredditWikiContributors(subName)
                wikicontributors.toString()
            }

            API_SUBREDDIT_MODERATORS -> {
                val subName = getRandomSubredditName()
                val moderators = client.subredditsClient.subredditModerators(subName)
                moderators.toString()
            }

            API_USER_OVERVIEW -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client.redditorsClient.createOverviewContributionsFetcher(userName)
                val overview = fetcher.fetchNext()
                overview.toString()
            }

            API_USER_SUBMITTED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client.redditorsClient.createSubmittedContributionsFetcher(userName)
                val submitted = fetcher.fetchNext()
                submitted.toString()
            }

            API_USER_COMMENTS -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client.redditorsClient.createCommentsContributionsFetcher(userName)
                val comments = fetcher.fetchNext()
                comments.toString()
            }

            API_USER_GILDED -> {
                val userName = getRandomUserFromRandomSubreddit()
                val fetcher = client.redditorsClient.createGildedContributionsFetcher(userName)
                val gilded = fetcher.fetchNext()
                gilded.toString()
            }

            API_USER_TROPHIES -> {
                val userName = getRandomUserFromRandomSubreddit()
                val trophies = client.redditorsClient.trophies(userName)
                trophies.toString()
            }

            API_WIKI -> {
                val subredditName = getRandomSubredditName()
                val wiki = client.wikisClient.wiki(subredditName)
                wiki.toString()
            }

            API_WIKI_PAGES -> {
                val subredditName = getRandomSubredditName()
                val wikiPages = client.wikisClient.wikiPages(subredditName)
                wikiPages.toString()
            }

            API_WIKI_REVISION -> {
                val subredditName = getRandomSubredditName()
                val wikiRevisions = client.wikisClient.wikiRevision(
                    subreddit = subredditName,
                    page = "index"
                )
                wikiRevisions.toString()
            }

            API_WIKI_REVISIONS -> {
                val subredditName = getRandomSubredditName()
                val wikiRevisions = client.wikisClient.wikiRevisions(subredditName)
                wikiRevisions.toString()
            }

            else -> "Api call not registered!"
        }
    }

    private fun getRandomSubredditName(): String {
        return subreddits.random()
    }

    private fun getRandomSubredditNames(): Array<String> {
        return subreddits
            .shuffled()
            .take(TAKE_SUB_NUM)
            .toTypedArray()
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
        val fetcher = client.contributionsClient.createSubmissionsFetcher(subredditName)
        val submissions = fetcher.fetchNext()

        return submissions
            ?.map { it.id }
            ?.randomOrNull()
            ?: ""
    }

    private fun getRandomUserFromRandomSubreddit(): String {
        val subredditName = getRandomSubredditName()
        val fetcher = client.contributionsClient.createSubmissionsFetcher(subredditName)
        val submissions = fetcher.fetchNext()

        return submissions
            ?.map { it.author }
            ?.toList()
            ?.randomOrNull()
            ?: ""
    }
}
