package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemSubredditBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class SubredditModel : ViewBindingEpoxyModelWithHolder<ItemSubredditBinding>() {

    @EpoxyAttribute
    lateinit var subreddit: String
    @EpoxyAttribute
    var subscribed: Boolean = false

    @EpoxyAttribute(DoNotHash)
    lateinit var subscribeClick: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_subreddit
    }

    override fun ItemSubredditBinding.bind() {

        title.text = subreddit

        subscribeButton.text = if (subscribed) "Subscribe" else "Unsubscribe"
        subscribeButton.setOnClickListener(subscribeClick)
    }

    override fun ItemSubredditBinding.unbind() {

        subscribeButton.setOnClickListener(null)
    }
}
