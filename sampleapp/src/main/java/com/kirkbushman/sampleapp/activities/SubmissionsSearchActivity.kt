package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.databinding.ActivitySubmissionsSearchBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubmissionsSearchActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionsSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var client: RedditClient

    private val submissions = ArrayList<Submission>()
    private val controller by lazy {

        SubmissionController(

            object : SubmissionController.SubmissionCallback {

                override fun onUpvoteClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.vote(Vote.UPVOTE, submission)
                        }
                    )
                }

                override fun onNoneClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.vote(Vote.NONE, submission)
                        }
                    )
                }

                override fun onDownClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.vote(Vote.DOWNVOTE, submission)
                        }
                    )
                }

                override fun onSaveClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.save(!submission.isSaved, submission)
                        }
                    )
                }

                override fun onHideClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.hide(submission)
                        }
                    )
                }

                override fun onLockClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client.contributionsClient.lock(submission)
                        }
                    )
                }

                override fun onReplyClick(index: Int) = Unit
            }
        )
    }

    private lateinit var binding: ActivitySubmissionsSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubmissionsSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.list.setHasFixedSize(true)
        binding.list.setController(controller)

        binding.searchBttn.setOnClickListener {

            val subreddit = binding.subreddit.text.toString().trim()
            val query = binding.query.text.toString().trim()
            val allSubs = binding.allSubs.isChecked

            DoAsync(
                doWork = {

                    val fetcher = client.searchClient.submissionsSearch(
                        subreddit = if (allSubs) null else subreddit,
                        query = query,
                        restrictToSubreddit = true
                    )

                    submissions.clear()
                    submissions.addAll(fetcher.fetchNext() ?: emptyList())
                },
                onPost = {
                    controller.setItems(submissions)
                }
            )
        }

        binding.allSubs.setOnCheckedChangeListener { _, checked ->

            if (checked) {
                binding.subreddit.visibility = View.INVISIBLE
            } else {
                binding.subreddit.visibility = View.VISIBLE
            }
        }
    }
}
