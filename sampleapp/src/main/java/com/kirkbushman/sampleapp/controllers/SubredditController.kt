package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.PrivateSubreddit
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.base.SubredditData
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.models.subreddit

class SubredditController(

    callback: SubredditCallback
) : BaseController<SubredditData, SubredditController.SubredditCallback>(callback) {

    interface SubredditCallback : BaseCallback {

        fun subscribeClick(index: Int)
    }

    override fun itemModel(index: Int, it: SubredditData, callback: SubredditCallback?) {

        when (it) {

            is Subreddit ->
                subreddit {
                    id(it.id)
                    subreddit(it.displayNamePrefixed)
                    subscribed(it.isSubscriber ?: false)
                    subscribeClick(View.OnClickListener { callback?.subscribeClick(index) })
                }

            is PrivateSubreddit ->
                subreddit {
                    id(it.id)
                    subreddit(it.displayNamePrefixed)
                    subscribed(false)
                    subscribeClick(View.OnClickListener { callback?.subscribeClick(index) })
                }
        }
    }
}
