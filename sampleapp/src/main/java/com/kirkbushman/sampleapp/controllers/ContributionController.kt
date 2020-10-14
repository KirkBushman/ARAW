package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.base.Contribution
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.submission

class ContributionController(private val callback: SubmissionController.SubmissionCallback) : EpoxyController() {

    private val contributions = ArrayList<Contribution>()

    fun setContributions(contributions: List<Contribution>) {
        this.contributions.clear()
        this.contributions.addAll(contributions)
        requestModelBuild()
    }

    override fun buildModels() {

        if (contributions.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        contributions.forEachIndexed { index, it ->

            if (it is Comment) {

                comment {
                    id(it.id)
                    author(it.author)
                    body(it.body)
                    replyClick { _ -> callback.onReplyClick(index) }
                }
            }

            if (it is Submission) {

                submission {
                    id(it.id)
                    subreddit(it.subreddit)
                    author(it.author)
                    title(getTaggedTitle(it))
                    body(it.selfText ?: "")

                    upvoteClick(View.OnClickListener { callback.onUpvoteClick(index) })
                    noneClick(View.OnClickListener { callback.onNoneClick(index) })
                    downvoteClick(View.OnClickListener { callback.onDownClick(index) })

                    saveClick(View.OnClickListener { callback.onSaveClick(index) })
                    hideClick(View.OnClickListener { callback.onHideClick(index) })
                    lockClick(View.OnClickListener { callback.onLockClick(index) })
                }
            }
        }
    }
}
