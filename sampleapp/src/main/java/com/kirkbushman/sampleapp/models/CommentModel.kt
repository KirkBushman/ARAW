package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemCommentBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class CommentModel : ViewBindingEpoxyModelWithHolder<ItemCommentBinding>() {

    @EpoxyAttribute
    lateinit var authorText: String
    @EpoxyAttribute
    lateinit var bodyText: String

    @EpoxyAttribute(DoNotHash)
    lateinit var replyClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_comment
    }

    override fun ItemCommentBinding.bind() {

        author.text = authorText
        body.text = bodyText

        replyButton.setOnClickListener(replyClick)
    }

    override fun ItemCommentBinding.unbind() {

        replyButton.setOnClickListener(null)
    }
}
