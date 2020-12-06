package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemMoreCommentsBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class MoreCommentModel : ViewBindingEpoxyModelWithHolder<ItemMoreCommentsBinding>() {

    @EpoxyAttribute
    lateinit var moreText: String
    @EpoxyAttribute(DoNotHash)
    lateinit var moreListener: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_more_comments
    }

    override fun ItemMoreCommentsBinding.bind() {

        more.text = moreText
        loadMoreButton.setOnClickListener(moreListener)
    }

    override fun ItemMoreCommentsBinding.unbind() {

        loadMoreButton.setOnClickListener(null)
    }
}
