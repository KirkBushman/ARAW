package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_subreddit)
abstract class SubredditModel : EpoxyModelWithHolder<SubredditHolder>() {

    @EpoxyAttribute
    lateinit var subreddit: String

    override fun bind(holder: SubredditHolder) {

        holder.title.text = subreddit
    }
}

class SubredditHolder : KotlinHolder() {

    val title by bind<TextView>(R.id.title)
}