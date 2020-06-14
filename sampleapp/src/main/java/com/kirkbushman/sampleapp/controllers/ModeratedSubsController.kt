package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.ModeratedSub
import com.kirkbushman.sampleapp.models.subreddit

class ModeratedSubsController(callback: SubredditController.SubredditCallback) : BaseController<ModeratedSub, SubredditController.SubredditCallback>(callback) {

    override fun itemModel(index: Int, it: ModeratedSub, callback: SubredditController.SubredditCallback?) {

        subreddit {
            id(it.fullname)
            subreddit(it.displayNamePrefixed)
            subscribed(it.isSubscriber)
            subscribeClick(View.OnClickListener { callback?.subscribeClick(index) })
        }
    }
}
