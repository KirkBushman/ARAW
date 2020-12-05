package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.controllers.RulesController

class RulesActivity : BaseSearchControllerActivity2<SubredditRule>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RulesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int? {
        return R.string.edit_insert_subreddit
    }

    override val controller: BaseController<SubredditRule, BaseCallback> = RulesController()

    override fun fetchItem(client: RedditClient?, query: String): Collection<SubredditRule>? {

        return client?.subredditsClient?.rules(query)?.toList()
    }
}
