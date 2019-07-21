package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.subreddit

class SubredditController(private val callback: SubredditCallback) : EpoxyController() {

    interface SubredditCallback {

        fun subscribeClick(index: Int)
    }

    private val subreddits = ArrayList<Subreddit>()

    fun setSubreddits(subreddits: List<Subreddit>) {
        this.subreddits.clear()
        this.subreddits.addAll(subreddits)
        requestModelBuild()
    }

    override fun buildModels() {

        if (subreddits.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        subreddits.forEachIndexed { index, it ->

            subreddit {
                id(it.id)
                subreddit(it.displayNamePrefixed)
                subscribed(it.isSubscriber)
                subscribeClick(View.OnClickListener { callback.subscribeClick(index) })
            }
        }
    }
}
