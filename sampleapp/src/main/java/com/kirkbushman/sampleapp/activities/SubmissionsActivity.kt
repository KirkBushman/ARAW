package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_submissions.*

class SubmissionsActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private val submissions = ArrayList<Submission>()
    private val controller by lazy { SubmissionController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        list.setController(controller)

        search_bttn.setOnClickListener {

            val subredditName = search.text.toString().trim()

            doAsync(doWork = {

                val fetcher = client?.submissions(subredditName)
                val temp = fetcher?.fetchNext() ?: listOf()
                submissions.addAll(temp)
            }, onPost = {

                controller.setSubmission(submissions)
            })
        }
    }
}