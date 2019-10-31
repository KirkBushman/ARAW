package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.moreComment
import com.kirkbushman.sampleapp.models.submission

class CommentController(private val callback: CommentCallback) : EpoxyController() {

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

        if (submission != null) {

            submission {
                id(submission!!.id)
                subreddit(submission!!.subreddit)
                author(submission!!.author)
                title(submission!!.title)
                body(submission!!.selfText ?: "")

                upvoteClick(View.OnClickListener { callback.onUpvoteClick(submission!!) })
                noneClick(View.OnClickListener { callback.onNoneClick(submission!!) })
                downvoteClick(View.OnClickListener { callback.onDownClick(submission!!) })

                saveClick(View.OnClickListener { callback.onSaveClick(submission!!) })
                hideClick(View.OnClickListener { callback.onHideClick(submission!!) })
                lockClick(View.OnClickListener { callback.onLockClick(submission!!) })
            }
        }

        comments.forEach {

            if (it is Comment) {

                comment {
                    id(it.id)
                    author(it.author)
                    body(it.body)
                    replyClick { _ -> callback.onReplyClick(it) }
                }
            }

            if (it is MoreComments) {

                moreComment {
                    id(it.fullname)
                    more("${it.count} more children")
                    clickListener { _ -> callback.onLoadMoreClick(it, submission!!) }
                }
            }
        }
    }
}
