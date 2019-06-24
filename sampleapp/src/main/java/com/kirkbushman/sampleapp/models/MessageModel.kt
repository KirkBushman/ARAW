package com.kirkbushman.sampleapp.models

import android.view.View
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

    @EpoxyAttribute lateinit var readBttn: View.OnClickListener
    @EpoxyAttribute lateinit var unreadBttn: View.OnClickListener

    override fun bind(holder: MessageHolder) {

        holder.subject.text = subject
        holder.author.text = author
        holder.body.text = body

        holder.readBttn.setOnClickListener(readBttn)
        holder.unreadBttn.setOnClickListener(unreadBttn)
    }

    override fun unbind(holder: MessageHolder) {

        holder.readBttn.setOnClickListener(null)
        holder.unreadBttn.setOnClickListener(null)
    }
}

class MessageHolder : KotlinHolder() {

    val subject by bind<TextView>(R.id.subject)
    val author by bind<TextView>(R.id.author)
    val body by bind<TextView>(R.id.body)

    val readBttn by bind<TextView>(R.id.read_button)
    val unreadBttn by bind<TextView>(R.id.unread_button)
}