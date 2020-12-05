package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.TrophiesController

class UserTrophiesActivity : BaseSearchControllerActivity2<Trophy>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UserTrophiesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.edit_insert_username
    }

    override val controller by lazy { TrophiesController() }

    override fun fetchItem(client: RedditClient?, query: String): Collection<Trophy>? {

        return client?.redditorsClient?.trophies(query)
    }
}
