package com.kirkbushman.sampleapp.models

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass
abstract class CommentModel : EpoxyModelWithHolder<CommentHolder>() {

    @EpoxyAttribute
    lateinit var author: String
    @EpoxyAttribute
    lateinit var body: String

    @EpoxyAttribute
    lateinit var replyClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_comment
    }

    override fun bind(holder: CommentHolder) {

        holder.author.text = author
        holder.body.text = body

        holder.reply.setOnClickListener(replyClick)
    }

    override fun unbind(holder: CommentHolder) {
        holder.reply.setOnClickListener(null)
    }
}

class CommentHolder : KotlinHolder() {

    val author by bind<TextView>(R.id.author)
    val body by bind<TextView>(R.id.body)
    val reply by bind<Button>(R.id.reply_button)
}
