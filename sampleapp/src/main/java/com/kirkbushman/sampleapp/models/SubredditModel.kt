package com.kirkbushman.sampleapp.models

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass
abstract class SubredditModel : EpoxyModelWithHolder<SubredditHolder>() {

    @EpoxyAttribute
    lateinit var subreddit: String

    @EpoxyAttribute
    var subscribed: Boolean = false

    @EpoxyAttribute(DoNotHash)
    lateinit var subscribeClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_subreddit
    }

    override fun bind(holder: SubredditHolder) {

        holder.title.text = subreddit

        holder.subscribe.text = if (subscribed) "Subscribe" else "Unsubscribe"
        holder.subscribe.setOnClickListener(subscribeClick)
    }

    override fun unbind(holder: SubredditHolder) {
        holder.subscribe.setOnClickListener(null)
    }
}

class SubredditHolder : KotlinHolder() {

    val title by bind<TextView>(R.id.title)
    val subscribe by bind<Button>(R.id.subscribe_button)
}
