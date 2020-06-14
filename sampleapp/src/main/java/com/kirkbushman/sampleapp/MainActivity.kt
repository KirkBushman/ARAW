package com.kirkbushman.sampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.activities.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bttn_back_to_login.setOnClickListener {

            LoginActivity.start(this, stopAutoLogin = true)
        }

        bttn_apis.setOnClickListener {

            ApisActivity.start(this)
        }

        bttn_me.setOnClickListener {

            SelfAccountActivity.start(this)
        }

        bttn_self_info.setOnClickListener {

            SelfAccountInfoActivity.start(this)
        }

        bttn_inbox.setOnClickListener {

            InboxActivity.start(this)
        }

        bttn_my_subreddits.setOnClickListener {

            SubscribedSubredditsActivity.start(this)
        }

        bttn_my_trophies.setOnClickListener {

            SelfTrophiesActivity.start(this)
        }

        bttn_user.setOnClickListener {

            RedditorActivity.start(this)
        }

        bttn_user_info.setOnClickListener {

            RedditorInfoActivity.start(this)
        }

        bttn_user_trophies.setOnClickListener {

            UserTrophiesActivity.start(this)
        }

        bttn_user_search.setOnClickListener {

            UserSearchActivity.start(this)
        }

        bttn_user_moderated_subs.setOnClickListener {

            RedditorModeratedSubs.start(this)
        }

        bttn_subreddit.setOnClickListener {

            SubredditActivity.start(this)
        }

        bttn_submission.setOnClickListener {

            SubmissionActivity.start(this)
        }

        bttn_submissions.setOnClickListener {

            SubmissionsActivity.start(this)
        }

        bttn_submissions_search.setOnClickListener {

            SubmissionsSearchActivity.start(this)
        }

        bttn_subreddit_search.setOnClickListener {

            SubredditsSearchActivity.start(this)
        }

        /*bttn_multi_search.setOnClickListener {

            val intent = Intent(this, MultiSearchActivity::class.java)
            startActivity(intent)
        }*/

        bttn_submit.setOnClickListener {

            SubmitActivity.start(this)
        }

        bttn_submit_media.setOnClickListener {

            SubmitMediaActivity.start(this)
        }

        bttn_submissions_commons.setOnClickListener {

            CommonSubmissionsActivity.start(this)
        }

        bttn_comment.setOnClickListener {

            CommentActivity.start(this)
        }

        bttn_comments.setOnClickListener {

            CommentsActivity.start(this)
        }

        bttn_wiki.setOnClickListener {

            WikiPageActivity.start(this)
        }

        bttn_wiki_pages.setOnClickListener {

            WikiPagesActivity.start(this)
        }

        bttn_rules.setOnClickListener {

            RulesActivity.start(this)
        }

        bttn_upload_images.setOnClickListener {

            UploadImagesActivity.start(this)
        }

        bttn_settings.setOnClickListener {

            SettingsActivity.start(this)
        }
    }
}
