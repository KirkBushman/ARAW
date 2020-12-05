package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity

class SubmissionActivity : BaseSearchPrintActivity<Submission>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.edit_insert_submission_id
    }

    override fun fetchItem(client: RedditClient?, query: String): Submission? {
        return client?.contributionsClient?.submission(query)
    }
}
