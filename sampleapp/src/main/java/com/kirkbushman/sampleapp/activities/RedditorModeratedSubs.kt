package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.ModeratedSub
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity
import com.kirkbushman.sampleapp.controllers.ModeratedSubsController
import com.kirkbushman.sampleapp.controllers.SubredditController

class RedditorModeratedSubs :
    BaseSearchControllerActivity<ModeratedSub, SubredditController.SubredditCallback>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RedditorModeratedSubs::class.java)
            context.startActivity(intent)
        }
    }

    override val callback = object : SubredditController.SubredditCallback {

        override fun subscribeClick(index: Int) = Unit
    }

    override val controller by lazy {

        ModeratedSubsController(callback)
    }

    override fun fetchItem(client: RedditClient?, query: String): Collection<ModeratedSub>? {

        return client?.redditorsClient?.moderatedSubreddits(query)
    }
}
