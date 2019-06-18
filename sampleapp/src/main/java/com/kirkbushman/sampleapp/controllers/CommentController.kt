package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.utils.toCommentSequence
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.moreComment
import com.kirkbushman.sampleapp.models.submission

class CommentController : EpoxyController() {

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
                body(submission!!.selftext ?: "")
            }
        }

        comments.toCommentSequence().forEach {

            if (it is Comment) {

                comment {
                    id(it.id)
                    author(it.author)
                    body(it.body)
                }
            }

            if (it is MoreComment) {

                moreComment {
                    id(it.name)
                    more("${it.count} more children")
                }
            }
        }
    }
}