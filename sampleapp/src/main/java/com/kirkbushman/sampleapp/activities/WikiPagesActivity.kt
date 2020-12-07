package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity
import com.kirkbushman.sampleapp.controllers.WikiPagesController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WikiPagesActivity :
    BaseSearchControllerActivity<String, WikiPagesController.WikiPageCallback>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, WikiPagesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var subreddit: String? = null

    override val callback = object : WikiPagesController.WikiPageCallback {

        override fun onPageClick(items: List<String>, index: Int) {

            WikiPageActivity.start(this@WikiPagesActivity, subreddit ?: "", items[index])
        }
    }

    override val controller by lazy {

        WikiPagesController(callback)
    }

    override fun fetchItem(client: RedditClient, query: String): Collection<String>? {

        subreddit = query

        return client.wikisClient.wikiPages(query)
    }
}
