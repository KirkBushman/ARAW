package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_more_comments)
abstract class MoreCommentModel : EpoxyModelWithHolder<MoreCommentHolder>() {

    @EpoxyAttribute
    lateinit var more: String

    override fun bind(holder: MoreCommentHolder) {

        holder.more.text = more
    }
}

class MoreCommentHolder : KotlinHolder() {

    val more by bind<TextView>(R.id.more)
}