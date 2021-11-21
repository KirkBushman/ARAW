package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.fetcher.CommentsFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.CommentsSorting
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.araw.models.base.CommentData
import com.kirkbushman.araw.utils.toLinearList
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.CommentController
import com.kirkbushman.sampleapp.databinding.ActivityCommentsBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import com.kirkbushman.sampleapp.fragments.ReplyBottomFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentsActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, CommentsActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var client: RedditClient

    private lateinit var binding: ActivityCommentsBinding

    private val comments = ArrayList<CommentData>()
    private val controller by lazy {

        CommentController(

            object : CommentController.CommentCallback {

                override fun onUpvoteClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.vote(Vote.UPVOTE, submission) }
                    )
                }

                override fun onNoneClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.vote(Vote.NONE, submission) }
                    )
                }

                override fun onDownClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.vote(Vote.DOWNVOTE, submission) }
                    )
                }

                override fun onSaveClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.save(!submission.isSaved, submission) }
                    )
                }

                override fun onHideClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.hide(submission) }
                    )
                }

                override fun onLockClick(submission: Submission) {

                    DoAsync(
                        doWork = { client.contributionsClient.lock(submission) }
                    )
                }

                override fun onLoadMoreClick(moreComments: MoreComments, submission: Submission) {

                    val addendum = ArrayList<CommentData>()

                    DoAsync(
                        doWork = {

                            val more = client.contributionsClient.moreChildren(moreComments, submission)
                            addendum.addAll(more ?: listOf())
                        },
                        onPost = {

                            replaceMoreComments(moreComments, addendum)
                        }
                    )
                }

                override fun onReplyClick(comment: Comment) {

                    val fr = ReplyBottomFragment.instance(comment)
                    fr.show(supportFragmentManager, "Reply Bottom Fragment")
                }
            }
        )
    }

    private var fetcher: CommentsFetcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.list.setHasFixedSize(true)
        binding.list.setController(controller)

        binding.searchBttn.setOnClickListener {

            val submissionId = binding.search.text.toString().trim()
            val commentId = binding.search2.text.toString().trim()

            DoAsync(
                doWork = {

                    val sanitizedCommentId = if (commentId != "") {
                        commentId
                    } else {
                        null
                    }

                    fetcher = client.contributionsClient.createCommentsFetcher(
                        submissionId = submissionId,
                        focusedCommentId = sanitizedCommentId
                    )

                    val temp = fetcher?.fetchNext() ?: listOf()

                    if (fetcher!!.getSubmission() != null) {
                        controller.setSubmission(fetcher!!.getSubmission()!!)
                    }

                    comments.clear()
                    comments.addAll(temp.toLinearList())
                },
                onPost = {

                    controller.setComments(comments)
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sorting_comments, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.item_sorting_best -> { reloadComments(sorting = CommentsSorting.BEST); true }
            R.id.item_sorting_confidence -> { reloadComments(sorting = CommentsSorting.CONFIDENCE); true }
            R.id.item_sorting_top -> { reloadComments(sorting = CommentsSorting.TOP); true }
            R.id.item_sorting_new -> { reloadComments(sorting = CommentsSorting.NEW); true }
            R.id.item_sorting_controversial -> { reloadComments(sorting = CommentsSorting.CONTROVERSIAL); true }
            R.id.item_sorting_old -> { reloadComments(sorting = CommentsSorting.OLD); true }
            R.id.item_sorting_random -> { reloadComments(sorting = CommentsSorting.RANDOM); true }
            R.id.item_sorting_qa -> { reloadComments(sorting = CommentsSorting.QA); true }
            R.id.item_sorting_live -> { reloadComments(sorting = CommentsSorting.LIVE); true }

            else -> { false }
        }
    }

    private fun replaceMoreComments(moreComments: MoreComments, addendum: List<CommentData>) {

        val index = comments.indexOf(moreComments)
        comments.remove(moreComments)
        comments.addAll(index, addendum)

        controller.setComments(comments)
    }

    private fun reloadComments(sorting: CommentsSorting? = null) {

        if (sorting != null) {

            DoAsync(
                doWork = {

                    fetcher!!.setSorting(sorting)

                    comments.clear()
                    comments.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setComments(comments)
                }
            )
        }
    }
}
