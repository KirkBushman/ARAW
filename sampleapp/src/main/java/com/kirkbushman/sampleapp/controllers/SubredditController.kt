package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.PrivateSubreddit
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.base.SubredditData
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.subreddit

class SubredditController(private val callback: SubredditCallback) : EpoxyController() {

    interface SubredditCallback : BaseCallback {

        fun subscribeClick(index: Int)
    }

    private val data = ArrayList<SubredditData>()
    private var searchResult: SubredditSearchResult? = null

    fun setSubreddits(subreddits: List<SubredditData>) {
        this.data.clear()
        this.data.addAll(subreddits)
        requestModelBuild()
    }

    fun setSearchResult(searchResult: SubredditSearchResult) {
        this.searchResult = searchResult
        requestModelBuild()
    }

    override fun buildModels() {

        if (data.isEmpty() && searchResult == null) {
            empty {
                id("empty_model")
            }
        }

        data.forEachIndexed { index, it ->

            when (it) {

                is Subreddit ->
                    subreddit {
                        id(it.id)
                        subreddit(it.displayNamePrefixed)
                        subscribed(it.isSubscriber ?: false)
                        subscribeClick(View.OnClickListener { callback.subscribeClick(index) })
                    }

                is PrivateSubreddit ->
                    subreddit {
                        id(it.id)
                        subreddit(it.displayNamePrefixed)
                        subscribed(false)
                        subscribeClick(View.OnClickListener { callback.subscribeClick(index) })
                    }
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
