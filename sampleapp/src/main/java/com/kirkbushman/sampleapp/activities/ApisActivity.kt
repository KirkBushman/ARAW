package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_apis.*

class ApisActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, ApisActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apis)

        bttn_me.setOnClickListener {
            ApiDetailActivity.startApiMe(this)
        }

        bttn_my_blocked.setOnClickListener {
            ApiDetailActivity.startApiMyBlocked(this)
        }

        bttn_my_friends.setOnClickListener {
            ApiDetailActivity.startApiMyFriends(this)
        }

        bttn_my_karma.setOnClickListener {
            ApiDetailActivity.startApiMyKarma(this)
        }

        bttn_my_prefs.setOnClickListener {
            ApiDetailActivity.startApiMyPrefs(this)
        }

        bttn_my_trophies.setOnClickListener {
            ApiDetailActivity.startApiMyTrophies(this)
        }

        bttn_overview.setOnClickListener {
            ApiDetailActivity.startApiOverview(this)
        }

        bttn_submitted.setOnClickListener {
            ApiDetailActivity.startApiSubmitted(this)
        }

        bttn_comments.setOnClickListener {
            ApiDetailActivity.startApiComments(this)
        }

        bttn_saved.setOnClickListener {
            ApiDetailActivity.startApiSaved(this)
        }

        bttn_hidden.setOnClickListener {
            ApiDetailActivity.startApiHidden(this)
        }

        bttn_upvoted.setOnClickListener {
            ApiDetailActivity.startApiUpvoted(this)
        }

        bttn_downvoted.setOnClickListener {
            ApiDetailActivity.startApiDownvoted(this)
        }

        bttn_gilded.setOnClickListener {
            ApiDetailActivity.startApiGilded(this)
        }

        bttn_sub_submission.setOnClickListener {
            ApiDetailActivity.startApiSubSubmission(this)
        }

        bttn_sub_comment.setOnClickListener {
            ApiDetailActivity.startApiSubComment(this)
        }

        bttn_sub_submissions.setOnClickListener {
            ApiDetailActivity.startApiSubSubmissions(this)
        }

        bttn_sub_comments.setOnClickListener {
            ApiDetailActivity.startApiSubComments(this)
        }

        bttn_multireddit.setOnClickListener {
            ApiDetailActivity.startApiSubMultireddit(this)
        }

        bttn_trending.setOnClickListener {
            ApiDetailActivity.startApiTrending(this)
        }

        bttn_inbox.setOnClickListener {
            ApiDetailActivity.startApiInbox(this)
        }

        bttn_unread.setOnClickListener {
            ApiDetailActivity.startApiUnread(this)
        }

        bttn_messages.setOnClickListener {
            ApiDetailActivity.startApiMessages(this)
        }

        bttn_sent.setOnClickListener {
            ApiDetailActivity.startApiSent(this)
        }

        bttn_comment_replies.setOnClickListener {
            ApiDetailActivity.startApiCommentReplies(this)
        }

        bttn_self_replies.setOnClickListener {
            ApiDetailActivity.startApiSelfReplies(this)
        }

        bttn_subreddit.setOnClickListener {
            ApiDetailActivity.startApiSubreddit(this)
        }

        bttn_subreddits.setOnClickListener {
            ApiDetailActivity.startApiSubreddits(this)
        }

        bttn_subreddit_banned.setOnClickListener {
            ApiDetailActivity.startApiSubBanned(this)
        }

        bttn_subreddit_muted.setOnClickListener {
            ApiDetailActivity.startApiSubMuted(this)
        }

        bttn_subreddit_wikibanned.setOnClickListener {
            ApiDetailActivity.startApiSubWikiBanned(this)
        }

        bttn_subreddit_contributors.setOnClickListener {
            ApiDetailActivity.startApiSubContributors(this)
        }

        bttn_subreddit_wikicontributors.setOnClickListener {
            ApiDetailActivity.startApiSubWikiContributors(this)
        }

        bttn_subreddit_moderators.setOnClickListener {
            ApiDetailActivity.startApiSubModerators(this)
        }

        bttn_user_overview.setOnClickListener {
            ApiDetailActivity.startApiUserOverview(this)
        }

        bttn_user_submitted.setOnClickListener {
            ApiDetailActivity.startApiUserSubmitted(this)
        }

        bttn_user_comments.setOnClickListener {
            ApiDetailActivity.startApiUserComments(this)
        }

        bttn_user_gilded.setOnClickListener {
            ApiDetailActivity.startApiUserGilded(this)
        }

        bttn_user_trophies.setOnClickListener {
            ApiDetailActivity.startApiUserTrophies(this)
        }

        bttn_wiki.setOnClickListener {
            ApiDetailActivity.startApiWiki(this)
        }

        bttn_wiki_pages.setOnClickListener {
            ApiDetailActivity.startApiWikiPages(this)
        }

        bttn_wiki_revision.setOnClickListener {
            ApiDetailActivity.startApiWikiRevision(this)
        }

        bttn_wiki_revisions.setOnClickListener {
            ApiDetailActivity.startApiWikiRevisions(this)
        }
    }
}
