package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemSubmissionBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class SubmissionModel : ViewBindingEpoxyModelWithHolder<ItemSubmissionBinding>() {

    @EpoxyAttribute
    lateinit var subredditText: String
    @EpoxyAttribute
    lateinit var authorText: String
    @EpoxyAttribute
    lateinit var titleText: String
    @EpoxyAttribute
    lateinit var bodyText: String

    @EpoxyAttribute(DoNotHash)
    lateinit var upvoteClick: View.OnClickListener
    @EpoxyAttribute(DoNotHash)
    lateinit var noneClick: View.OnClickListener
    @EpoxyAttribute(DoNotHash)
    lateinit var downvoteClick: View.OnClickListener

    @EpoxyAttribute(DoNotHash)
    lateinit var saveClick: View.OnClickListener
    @EpoxyAttribute(DoNotHash)
    lateinit var hideClick: View.OnClickListener
    @EpoxyAttribute(DoNotHash)
    lateinit var lockClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_submission
    }

    override fun ItemSubmissionBinding.bind() {

        subreddit.text = subredditText
        author.text = authorText
        title.text = titleText
        body.text = bodyText

        upvoteButton.setOnClickListener(upvoteClick)
        noneButton.setOnClickListener(noneClick)
        downvoteButton.setOnClickListener(downvoteClick)

        saveButton.setOnClickListener(saveClick)
        hideButton.setOnClickListener(hideClick)
        lockButton.setOnClickListener(lockClick)
    }

    override fun ItemSubmissionBinding.unbind() {

        subreddit.text = null
        author.text = null
        title.text = null
        body.text = null

        upvoteButton.setOnClickListener(null)
        noneButton.setOnClickListener(null)
        downvoteButton.setOnClickListener(null)

        saveButton.setOnClickListener(null)
        hideButton.setOnClickListener(null)
        lockButton.setOnClickListener(null)
    }
}
