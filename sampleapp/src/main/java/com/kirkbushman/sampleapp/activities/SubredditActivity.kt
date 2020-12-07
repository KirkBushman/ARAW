package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.base.SubredditData
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditActivity : BaseSearchPrintActivity<SubredditData>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubredditActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.edit_insert_subreddit
    }

    override fun fetchItem(client: RedditClient, query: String): SubredditData? {
        return client.subredditsClient.subreddit(query)
    }
}
