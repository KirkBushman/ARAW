package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_submission.*

class SubmissionActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        bttn_search.setOnClickListener {

            val submissionId = edit_submission_id.text.toString().trim()
            if (submissionId.isNotEmpty()) {

                var submission: Submission? = null
                doAsync(doWork = {

                    submission = client?.contributionClient?.submission(submissionId)
                }, onPost = {

                    submission_text.text = submission.toString()
                })
            }
        }
    }
}
