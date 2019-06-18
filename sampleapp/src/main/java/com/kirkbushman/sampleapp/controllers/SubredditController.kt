package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.subreddit

class SubredditController : EpoxyController() {

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

        subreddits.forEach {

            subreddit {
                id(it.id)
                subreddit(it.displayNamePrefixed)
            }
        }
    }
}