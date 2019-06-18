package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_comment)
abstract class CommentModel : EpoxyModelWithHolder<CommentHolder>() {

    @EpoxyAttribute
    lateinit var author: String
    @EpoxyAttribute
    lateinit var body: String

    override fun bind(holder: CommentHolder) {

        holder.author.text = author
        holder.body.text = body
    }
}

class CommentHolder : KotlinHolder() {

    val author by bind<TextView>(R.id.author)
    val body by bind<TextView>(R.id.body)
}