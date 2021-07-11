package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.base.CommentData
import com.kirkbushman.araw.utils.treeIterator
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.moreComment
import com.kirkbushman.sampleapp.models.submission

class CommentController(

    private val callback: CommentCallback

) : EpoxyController() {

    interface CommentCallback {

        fun onUpvoteClick(submission: Submission)
        fun onNoneClick(submission: Submission)
        fun onDownClick(submission: Submission)

        fun onSaveClick(submission: Submission)
        fun onHideClick(submission: Submission)
        fun onLockClick(submission: Submission)

        fun onLoadMoreClick(moreComments: MoreComments, submission: Submission)

        fun onReplyClick(comment: Comment)
    }

    private val comments = ArrayList<CommentData>()
    private var submission: Submission? = null

    fun setSubmission(submission: Submission?) {
        this.submission = submission
    }

    fun setComments(comments: List<CommentData>) {
        this.comments.clear()
        this.comments.addAll(comments)

        requestModelBuild()
    }

    override fun buildModels() {

        if (comments.isEmpty()) {

            empty {
                id("empty_model")
            }
        }

        submission?.let {

            submission {
                id(it.id)
                subredditText(it.subreddit)
                authorText(it.author)
                titleText(it.title)
                bodyText(it.selfText ?: "")

                upvoteClick { _ -> this@CommentController.callback.onUpvoteClick(it) }
                noneClick { _ -> this@CommentController.callback.onNoneClick(it) }
                downvoteClick { _ -> this@CommentController.callback.onDownClick(it) }

                saveClick { _ -> this@CommentController.callback.onSaveClick(it) }
                hideClick { _ -> this@CommentController.callback.onHideClick(it) }
                lockClick { _ -> this@CommentController.callback.onLockClick(it) }
            }
        }

        comments
            .treeIterator()
            .forEach {

                if (it is Comment) {

                    comment {
                        id(it.id)
                        authorText(it.author)
                        bodyText(it.body)
                        replyClick { _ ->
                            this@CommentController.callback.onReplyClick(it)
                        }
                    }
                }

                if (it is MoreComments) {

                    moreComment {
                        id(it.fullname)
                        moreText("${it.count} more children")
                        moreListener { _ ->
                            this@CommentController.callback.onLoadMoreClick(it, this@CommentController.submission!!)
                        }
                    }
                }
            }
    }
}
