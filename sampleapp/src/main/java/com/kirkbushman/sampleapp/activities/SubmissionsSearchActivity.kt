package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.util.doAsync
import kotlinx.android.synthetic.main.activity_submissions_search.*

class SubmissionsSearchActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionsSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val submissions = ArrayList<Submission>()
    private val controller by lazy {
        SubmissionController(object : SubmissionController.SubmissionCallback {

            override fun onUpvoteClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.vote(Vote.UPVOTE, submission)
                })
            }

            override fun onNoneClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.vote(Vote.NONE, submission)
                })
            }

            override fun onDownClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.vote(Vote.DOWNVOTE, submission)
                })
            }

            override fun onSaveClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.save(!submission.isSaved, submission)
                })
            }

            override fun onHideClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.hide(submission)
                })
            }

            override fun onLockClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributionsClient?.lock(submission)
                })
            }

            override fun onReplyClick(index: Int) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions_search)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val subreddit = subreddit.text.toString().trim()
            val query = query.text.toString().trim()
            val allSubs = all_subs.isChecked

            doAsync(doWork = {

                val fetcher = client?.contributionsClient?.submissionsSearch(
                    if (allSubs) null else subreddit,
                    query
                )

                submissions.clear()
                submissions.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {

                controller.setSubmission(submissions)
            })
        }

        all_subs.setOnCheckedChangeListener { _, checked ->

            if (checked) {
                subreddit.visibility = View.INVISIBLE
            } else {
                subreddit.visibility = View.VISIBLE
            }
        }
    }
}
