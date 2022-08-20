package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.databinding.ActivityApisBinding

class ApisActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ApisActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityApisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttnMe.setOnClickListener {
            ApiDetailActivity.startApiMe(this)
        }

        binding.bttnMyBlocked.setOnClickListener {
            ApiDetailActivity.startApiMyBlocked(this)
        }

        binding.bttnMyFriends.setOnClickListener {
            ApiDetailActivity.startApiMyFriends(this)
        }

        binding.bttnMyKarma.setOnClickListener {
            ApiDetailActivity.startApiMyKarma(this)
        }

        binding.bttnMyPrefs.setOnClickListener {
            ApiDetailActivity.startApiMyPrefs(this)
        }

        binding.bttnMyTrophies.setOnClickListener {
            ApiDetailActivity.startApiMyTrophies(this)
        }

        binding.bttnOverview.setOnClickListener {
            ApiDetailActivity.startApiOverview(this)
        }

        binding.bttnSubmitted.setOnClickListener {
            ApiDetailActivity.startApiSubmitted(this)
        }

        binding.bttnComments.setOnClickListener {
            ApiDetailActivity.startApiComments(this)
        }

        binding.bttnSaved.setOnClickListener {
            ApiDetailActivity.startApiSaved(this)
        }

        binding.bttnHidden.setOnClickListener {
            ApiDetailActivity.startApiHidden(this)
        }

        binding.bttnUpvoted.setOnClickListener {
            ApiDetailActivity.startApiUpvoted(this)
        }

        binding.bttnDownvoted.setOnClickListener {
            ApiDetailActivity.startApiDownvoted(this)
        }

        binding.bttnGilded.setOnClickListener {
            ApiDetailActivity.startApiGilded(this)
        }

        binding.bttnSubSubmission.setOnClickListener {
            ApiDetailActivity.startApiSubSubmission(this)
        }

        binding.bttnSubComment.setOnClickListener {
            ApiDetailActivity.startApiSubComment(this)
        }

        binding.bttnSubSubmissions.setOnClickListener {
            ApiDetailActivity.startApiSubSubmissions(this)
        }

        binding.bttnSubComments.setOnClickListener {
            ApiDetailActivity.startApiSubComments(this)
        }

        binding.bttnMultireddit.setOnClickListener {
            ApiDetailActivity.startApiSubMultireddit(this)
        }

        binding.bttnTrending.setOnClickListener {
            ApiDetailActivity.startApiTrending(this)
        }

        binding.bttnInbox.setOnClickListener {
            ApiDetailActivity.startApiInbox(this)
        }

        binding.bttnUnread.setOnClickListener {
            ApiDetailActivity.startApiUnread(this)
        }

        binding.bttnMessages.setOnClickListener {
            ApiDetailActivity.startApiMessages(this)
        }

        binding.bttnSent.setOnClickListener {
            ApiDetailActivity.startApiSent(this)
        }

        binding.bttnCommentReplies.setOnClickListener {
            ApiDetailActivity.startApiCommentReplies(this)
        }

        binding.bttnSelfReplies.setOnClickListener {
            ApiDetailActivity.startApiSelfReplies(this)
        }

        binding.bttnMyMultis.setOnClickListener {
            ApiDetailActivity.startApiMyMultis(this)
        }

        binding.bttnRedditorMultis.setOnClickListener {
            ApiDetailActivity.startApiRedditorMultis(this)
        }

        binding.bttnMultiSubmissions.setOnClickListener {
            ApiDetailActivity.startApiMultiSubs(this)
        }

        binding.bttnMultiGetDesc.setOnClickListener {
            ApiDetailActivity.startApiMultiGetDesc(this)
        }

        binding.bttnMultiSetDesc.setOnClickListener {
            ApiDetailActivity.startApiMultiSetDesc(this)
        }

        binding.bttnMultiGetSub.setOnClickListener {
            ApiDetailActivity.startApiMultiGetSub(this)
        }

        binding.bttnMultiAddSub.setOnClickListener {
            ApiDetailActivity.startApiMultiAddSub(this)
        }

        binding.bttnMultiDelSub.setOnClickListener {
            ApiDetailActivity.startApiMultiDelSub(this)
        }

        binding.bttnMulti.setOnClickListener {
            ApiDetailActivity.startApiMulti(this)
        }

        binding.bttnDelMulti.setOnClickListener {
            ApiDetailActivity.startApiDelMulti(this)
        }

        binding.bttnSubreddit.setOnClickListener {
            ApiDetailActivity.startApiSubreddit(this)
        }

        binding.bttnSubreddits.setOnClickListener {
            ApiDetailActivity.startApiSubreddits(this)
        }

        binding.bttnSubredditBanned.setOnClickListener {
            ApiDetailActivity.startApiSubBanned(this)
        }

        binding.bttnSubredditMuted.setOnClickListener {
            ApiDetailActivity.startApiSubMuted(this)
        }

        binding.bttnSubredditRules.setOnClickListener {
            ApiDetailActivity.startApiSubRules(this)
        }

        binding.bttnSubredditFlairs.setOnClickListener {
            ApiDetailActivity.startApiSubFlairs(this)
        }

        binding.bttnSubredditWikibanned.setOnClickListener {
            ApiDetailActivity.startApiSubWikiBanned(this)
        }

        binding.bttnSubredditContributors.setOnClickListener {
            ApiDetailActivity.startApiSubContributors(this)
        }

        binding.bttnSubredditWikicontributors.setOnClickListener {
            ApiDetailActivity.startApiSubWikiContributors(this)
        }

        binding.bttnSubredditModerators.setOnClickListener {
            ApiDetailActivity.startApiSubModerators(this)
        }

        binding.bttnUserOverview.setOnClickListener {
            ApiDetailActivity.startApiUserOverview(this)
        }

        binding.bttnUserSubmitted.setOnClickListener {
            ApiDetailActivity.startApiUserSubmitted(this)
        }

        binding.bttnUserComments.setOnClickListener {
            ApiDetailActivity.startApiUserComments(this)
        }

        binding.bttnUserGilded.setOnClickListener {
            ApiDetailActivity.startApiUserGilded(this)
        }

        binding.bttnUserTrophies.setOnClickListener {
            ApiDetailActivity.startApiUserTrophies(this)
        }

        binding.bttnWiki.setOnClickListener {
            ApiDetailActivity.startApiWiki(this)
        }

        binding.bttnWikiPages.setOnClickListener {
            ApiDetailActivity.startApiWikiPages(this)
        }

        binding.bttnWikiRevision.setOnClickListener {
            ApiDetailActivity.startApiWikiRevision(this)
        }

        binding.bttnWikiRevisions.setOnClickListener {
            ApiDetailActivity.startApiWikiRevisions(this)
        }
    }
}
