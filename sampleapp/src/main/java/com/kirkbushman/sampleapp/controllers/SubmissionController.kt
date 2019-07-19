package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.utils.vote
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.submission

class SubmissionController(private val callback: SubmissionCallback) : EpoxyController() {

    interface SubmissionCallback {

        fun onUpvoteClick(index: Int)
        fun onNoneClick(index: Int)
        fun onDownClick(index: Int)

        fun onSaveClick(index: Int)
    }

    private val submissions = ArrayList<Submission>()

    fun setSubmission(submissions: List<Submission>) {
        this.submissions.clear()
        this.submissions.addAll(submissions)
        requestModelBuild()
    }

    override fun buildModels() {

        if (submissions.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        submissions.forEachIndexed { index, it ->

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
            }
        }
    }
}

fun getTaggedTitle(submission: Submission): String {

    var out = ""

    if (submission.vote != Vote.NONE) {
        out += "[${submission.vote}]"
    }

    if (submission.isSaved) {
        out += "[SAVED]"
    }

    out += " "
    out += submission.title

    return out
}
