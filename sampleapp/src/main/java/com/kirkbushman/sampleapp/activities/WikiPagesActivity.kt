package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.WikiPagesController
import kotlinx.android.synthetic.main.activity_wiki_pages.*

class WikiPagesActivity : BaseSearchControllerActivity<String, WikiPagesController.WikiPageCallback>(R.layout.activity_wiki_pages) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, WikiPagesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var subreddit: String? = null

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = search_bttn

    override val editSearch: EditText
        get() = search

    override val recyclerView: EpoxyRecyclerView
        get() = list

    override val callback: WikiPagesController.WikiPageCallback?
        get() = object : WikiPagesController.WikiPageCallback {

            override fun onPageClick(items: List<String>, index: Int) {

                WikiPageActivity.start(this@WikiPagesActivity, subreddit ?: "", items[index])
            }
        }

    override val controller: BaseController<String, WikiPagesController.WikiPageCallback>
        get() = WikiPagesController(callback!!)

    override fun fetchItem(client: RedditClient?, query: String): Collection<String>? {

        subreddit = query

        return client?.wikisClient?.wikiPages(query)
    }
}
