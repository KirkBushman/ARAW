package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.activities.base.BaseControllerActivity2
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.controllers.TrophiesController

class SelfTrophiesActivity : BaseControllerActivity2<Trophy>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfTrophiesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val controller: BaseController<Trophy, BaseCallback> = TrophiesController()

    override fun fetchItem(client: RedditClient?): Collection<Trophy>? {

        return client?.accountsClient?.myTrophies()
    }
}
