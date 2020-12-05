package com.kirkbushman.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.activities.*
import com.kirkbushman.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttnBackToLogin.setOnClickListener {

            LoginActivity.start(this, stopAutoLogin = true)
        }

        binding.bttnApis.setOnClickListener {

            ApisActivity.start(this)
        }

        binding.bttnMe.setOnClickListener {

            SelfAccountActivity.start(this)
        }

        binding.bttnSelfInfo.setOnClickListener {

            SelfAccountInfoActivity.start(this)
        }

        binding.bttnInbox.setOnClickListener {

            InboxActivity.start(this)
        }

        binding.bttnMySubreddits.setOnClickListener {

            SubscribedSubredditsActivity.start(this)
        }

        binding.bttnMyTrophies.setOnClickListener {

            SelfTrophiesActivity.start(this)
        }

        binding.bttnUser.setOnClickListener {

            RedditorActivity.start(this)
        }

        binding.bttnUserInfo.setOnClickListener {

            RedditorInfoActivity.start(this)
        }

        binding.bttnUserTrophies.setOnClickListener {

            UserTrophiesActivity.start(this)
        }

        binding.bttnUserSearch.setOnClickListener {

            UserSearchActivity.start(this)
        }

        binding.bttnUserModeratedSubs.setOnClickListener {

            RedditorModeratedSubs.start(this)
        }

        binding.bttnSubreddit.setOnClickListener {

            SubredditActivity.start(this)
        }

        binding.bttnSubmission.setOnClickListener {

            SubmissionActivity.start(this)
        }

        binding.bttnSubmissions.setOnClickListener {

            SubmissionsActivity.start(this)
        }

        binding.bttnSubmissionsSearch.setOnClickListener {

            SubmissionsSearchActivity.start(this)
        }

        binding.bttnSubmissionsFilter.setOnClickListener {

            SubmissionsFlairFilterActivity.start(this)
        }

        binding.bttnMultiSubmissions.setOnClickListener {

            MultiSubmissionsActivity.start(this)
        }

        binding.bttnSubredditSearch.setOnClickListener {

            SubredditsSearchActivity.start(this)
        }

        /*bttn_multi_search.setOnClickListener {

            val intent = Intent(this, MultiSearchActivity::class.java)
            startActivity(intent)
        }*/

        binding.bttnSubmit.setOnClickListener {

            SubmitActivity.start(this)
        }

        binding.bttnSubmitMedia.setOnClickListener {

            SubmitMediaActivity.start(this)
        }

        binding.bttnSubmissionsCommons.setOnClickListener {

            CommonSubmissionsActivity.start(this)
        }

        binding.bttnComment.setOnClickListener {

            CommentActivity.start(this)
        }

        binding.bttnComments.setOnClickListener {

            CommentsActivity.start(this)
        }

        /*bttn_poll_vote.setOnClickListener {

            PollVoteActivity.start(this)
        }*/

        binding.bttnWiki.setOnClickListener {

            WikiPageActivity.start(this)
        }

        binding.bttnWikiPages.setOnClickListener {

            WikiPagesActivity.start(this)
        }

        binding.bttnRules.setOnClickListener {

            RulesActivity.start(this)
        }

        binding.bttnUploadImages.setOnClickListener {

            UploadImagesActivity.start(this)
        }

        binding.bttnSettings.setOnClickListener {

            SettingsActivity.start(this)
        }
    }
}
