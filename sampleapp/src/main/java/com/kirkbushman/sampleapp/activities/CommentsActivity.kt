package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.CommentController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.activity_comments.list
import kotlinx.android.synthetic.main.fragment_inbox.*

class CommentsActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private val comments = ArrayList<CommentData>()
    private val controller by lazy {
        CommentController(object : CommentController.CommentCallback {

            override fun onUpvoteClick(submission: Submission) {
                client?.contributions?.vote(Vote.UPVOTE, submission)
            }

            override fun onNoneClick(submission: Submission) {
                client?.contributions?.vote(Vote.NONE, submission)
            }

            override fun onDownClick(submission: Submission) {
                client?.contributions?.vote(Vote.DOWNVOTE, submission)
            }

            override fun onSaveClick(submission: Submission) {
                client?.contributions?.save(!submission.isSaved, submission)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val submissionId = search.text.toString().trim()

            doAsync(doWork = {

                val fetcher = client?.comments(submissionId)
                val temp = fetcher?.fetchNext() ?: listOf()

                if (fetcher!!.getSubmission() != null) {
                    controller.setSubmission(fetcher.getSubmission()!!)
                }

                comments.addAll(temp)
            }, onPost = {

                controller.setComments(comments)
            })
        }
    }
}