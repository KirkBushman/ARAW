package com.kirkbushman.sampleapp.models

import android.view.View
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

    @EpoxyAttribute lateinit var upvoteClick: View.OnClickListener
    @EpoxyAttribute lateinit var noneClick: View.OnClickListener
    @EpoxyAttribute lateinit var downvoteClick: View.OnClickListener

    @EpoxyAttribute lateinit var saveClick: View.OnClickListener

    override fun bind(holder: SubmissionHolder) {

        holder.subreddit.text = subreddit
        holder.author.text = author
        holder.title.text = title
        holder.body.text = body

        holder.upvote.setOnClickListener(upvoteClick)
        holder.none.setOnClickListener(noneClick)
        holder.downvote.setOnClickListener(downvoteClick)

        holder.save.setOnClickListener(saveClick)
    }

    override fun unbind(holder: SubmissionHolder) {

        holder.upvote.setOnClickListener(null)
        holder.none.setOnClickListener(null)
        holder.downvote.setOnClickListener(null)

        holder.save.setOnClickListener(null)
    }
}

class SubmissionHolder : KotlinHolder() {

    val subreddit by bind<TextView>(R.id.subreddit)
    val author by bind<TextView>(R.id.author)
    val title by bind<TextView>(R.id.title)
    val body by bind<TextView>(R.id.body)

    val upvote by bind<TextView>(R.id.upvote_button)
    val none by bind<TextView>(R.id.none_button)
    val downvote by bind<TextView>(R.id.downvote_button)

    val save by bind<TextView>(R.id.save_button)
}