package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_message)
abstract class MessageModel : EpoxyModelWithHolder<MessageHolder>() {

    @EpoxyAttribute lateinit var subject: String
    @EpoxyAttribute lateinit var author: String
    @EpoxyAttribute lateinit var body: String

    override fun bind(holder: MessageHolder) {

        holder.subject.text = subject
        holder.author.text = author
        holder.body.text = body
    }
}

class MessageHolder : KotlinHolder() {

    val subject by bind<TextView>(R.id.subject)
    val author by bind<TextView>(R.id.author)
    val body by bind<TextView>(R.id.body)
}