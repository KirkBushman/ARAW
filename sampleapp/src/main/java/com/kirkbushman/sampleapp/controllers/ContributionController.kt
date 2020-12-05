package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.base.Contribution
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.submission

class ContributionController(

    callback: SubmissionController.SubmissionCallback
) : BaseController<Contribution, SubmissionController.SubmissionCallback>(callback) {

    override fun itemModel(
        index: Int,
        it: Contribution,
        callback: SubmissionController.SubmissionCallback?
    ) {

        if (it is Comment) {

            comment {
                id(it.id)
                authorText(it.author)
                bodyText(it.body)
                replyClick { _ -> callback?.onReplyClick(index) }
            }
        }

        if (it is Submission) {

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
}
