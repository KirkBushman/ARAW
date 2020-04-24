package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.subreddit

class SubredditController(private val callback: SubredditCallback) : EpoxyController() {

    interface SubredditCallback {

        fun subscribeClick(index: Int)
    }

    private val subreddits = ArrayList<Subreddit>()
    private var searchResult: SubredditSearchResult? = null

    fun setSubreddits(subreddits: List<Subreddit>) {
        this.subreddits.clear()
        this.subreddits.addAll(subreddits)
        requestModelBuild()
    }

    fun setSearchResult(searchResult: SubredditSearchResult) {
        this.searchResult = searchResult
        requestModelBuild()
    }

    override fun buildModels() {

        if (subreddits.isEmpty() && searchResult == null) {
            empty {
                id("empty_model")
            }
        }

        subreddits.forEachIndexed { index, it ->

            subreddit {
                id(it.id)
                subreddit(it.displayNamePrefixed)
                subscribed(it.isSubscriber ?: false)
                subscribeClick(View.OnClickListener { callback.subscribeClick(index) })
            }
        }

        if (searchResult != null) {
            searchResult!!.subreddits.forEach {

                subreddit {
                    id(it.name)
                    subreddit(it.name)
                    subscribed(false)
                    subscribeClick(View.OnClickListener {})
                }
            }
        }
    }
}
