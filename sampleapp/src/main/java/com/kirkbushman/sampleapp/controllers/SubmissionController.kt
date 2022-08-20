package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.araw.utils.vote
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.models.submission

class SubmissionController(

    callback: SubmissionCallback

) : BaseController<Submission, SubmissionController.SubmissionCallback>(callback) {

    interface SubmissionCallback : BaseCallback {
        fun onUpvoteClick(index: Int)
        fun onNoneClick(index: Int)
        fun onDownClick(index: Int)

        fun onSaveClick(index: Int)
        fun onHideClick(index: Int)
        fun onLockClick(index: Int)
        fun onReplyClick(index: Int)
    }

    override fun itemModel(index: Int, it: Submission, callback: SubmissionCallback?) {
        submission {
            id(it.id)
            subredditText(it.subreddit)
            authorText(it.author)
            titleText(getTaggedTitle(it))
            bodyText(it.selfText ?: "")

            upvoteClick(View.OnClickListener { callback?.onUpvoteClick(index) })
            noneClick(View.OnClickListener { callback?.onNoneClick(index) })
            downvoteClick(View.OnClickListener { callback?.onDownClick(index) })

            saveClick(View.OnClickListener { callback?.onSaveClick(index) })
            hideClick(View.OnClickListener { callback?.onHideClick(index) })
            lockClick(View.OnClickListener { callback?.onLockClick(index) })
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
