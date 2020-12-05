package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemMessageBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class MessageModel : ViewBindingEpoxyModelWithHolder<ItemMessageBinding>() {

    @EpoxyAttribute lateinit var subjectText: String
    @EpoxyAttribute lateinit var authorText: String
    @EpoxyAttribute lateinit var bodyText: String

    @EpoxyAttribute(DoNotHash)
    lateinit var readClick: View.OnClickListener
    @EpoxyAttribute(DoNotHash)
    lateinit var unreadClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_message
    }

    override fun ItemMessageBinding.bind() {

        subject.text = subjectText
        author.text = authorText
        body.text = bodyText

        readButton.setOnClickListener(readClick)
        unreadButton.setOnClickListener(unreadClick)
    }

    override fun ItemMessageBinding.unbind() {

        readButton.setOnClickListener(null)
        unreadButton.setOnClickListener(null)
    }
}
