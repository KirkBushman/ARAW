package com.kirkbushman.sampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.activities.CommentActivity
import com.kirkbushman.sampleapp.activities.CommentsActivity
import com.kirkbushman.sampleapp.activities.CommonSubmissionsActivity
import com.kirkbushman.sampleapp.activities.InboxActivity
import com.kirkbushman.sampleapp.activities.RedditorActivity
import com.kirkbushman.sampleapp.activities.RedditorInfoActivity
import com.kirkbushman.sampleapp.activities.RulesActivity
import com.kirkbushman.sampleapp.activities.SelfAccountActivity
import com.kirkbushman.sampleapp.activities.SelfAccountInfoActivity
import com.kirkbushman.sampleapp.activities.SelfTrophiesActivity
import com.kirkbushman.sampleapp.activities.SubmissionActivity
import com.kirkbushman.sampleapp.activities.SubmissionsActivity
import com.kirkbushman.sampleapp.activities.SubmissionsSearchActivity
import com.kirkbushman.sampleapp.activities.SubredditActivity
import com.kirkbushman.sampleapp.activities.SubscribedSubredditsActivity
import com.kirkbushman.sampleapp.activities.UserTrophiesActivity
import com.kirkbushman.sampleapp.activities.WikiPageActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bttn_me.setOnClickListener {

            val intent = Intent(this, SelfAccountActivity::class.java)
            startActivity(intent)
        }

        bttn_self_info.setOnClickListener {

            val intent = Intent(this, SelfAccountInfoActivity::class.java)
            startActivity(intent)
        }

        bttn_inbox.setOnClickListener {

            val intent = Intent(this, InboxActivity::class.java)
            startActivity(intent)
        }

        bttn_my_subreddits.setOnClickListener {

            val intent = Intent(this, SubscribedSubredditsActivity::class.java)
            startActivity(intent)
        }

        bttn_my_trophies.setOnClickListener {

            val intent = Intent(this, SelfTrophiesActivity::class.java)
            startActivity(intent)
        }

        bttn_user.setOnClickListener {

            val intent = Intent(this, RedditorActivity::class.java)
            startActivity(intent)
        }

        bttn_user_info.setOnClickListener {

            val intent = Intent(this, RedditorInfoActivity::class.java)
            startActivity(intent)
        }

        bttn_user_trophies.setOnClickListener {

            val intent = Intent(this, UserTrophiesActivity::class.java)
            startActivity(intent)
        }

        bttn_subreddit.setOnClickListener {

            val intent = Intent(this, SubredditActivity::class.java)
            startActivity(intent)
        }

        bttn_submission.setOnClickListener {

            val intent = Intent(this, SubmissionActivity::class.java)
            startActivity(intent)
        }

        bttn_submissions.setOnClickListener {

            val intent = Intent(this, SubmissionsActivity::class.java)
            startActivity(intent)
        }

        bttn_submissions_search.setOnClickListener {

            val intent = Intent(this, SubmissionsSearchActivity::class.java)
            startActivity(intent)
        }

        bttn_submissions_commons.setOnClickListener {

            val intent = Intent(this, CommonSubmissionsActivity::class.java)
            startActivity(intent)
        }

        bttn_comment.setOnClickListener {

            val intent = Intent(this, CommentActivity::class.java)
            startActivity(intent)
        }

        bttn_comments.setOnClickListener {

            val intent = Intent(this, CommentsActivity::class.java)
            startActivity(intent)
        }

        bttn_wiki.setOnClickListener {

            val intent = Intent(this, WikiPageActivity::class.java)
            startActivity(intent)
        }

        bttn_rules.setOnClickListener {

            val intent = Intent(this, RulesActivity::class.java)
            startActivity(intent)
        }
    }
}
