package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_submission)
abstract class SubmissionModel : EpoxyModelWithHolder<SubmissionHolder>() {

    @EpoxyAttribute
    lateinit var subreddit: String
    @EpoxyAttribute
    lateinit var author: String
    @EpoxyAttribute
    lateinit var title: String
    @EpoxyAttribute
    lateinit var body: String

    override fun bind(holder: SubmissionHolder) {

        holder.subreddit.text = subreddit
        holder.author.text = author
        holder.title.text = title
        holder.body.text = body
    }
}

class SubmissionHolder : KotlinHolder() {

    val subreddit by bind<TextView>(R.id.subreddit)
    val author by bind<TextView>(R.id.author)
    val title by bind<TextView>(R.id.title)
    val body by bind<TextView>(R.id.body)
}