package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.ModeratedSub
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.ModeratedSubsController
import com.kirkbushman.sampleapp.controllers.SubredditController
import kotlinx.android.synthetic.main.activity_wiki_pages.*

class RedditorModeratedSubs : BaseSearchControllerActivity<ModeratedSub, SubredditController.SubredditCallback>(R.layout.activity_moderated_subs) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RedditorModeratedSubs::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = search_bttn

    override val editSearch: EditText
        get() = search

    override val recyclerView: EpoxyRecyclerView
        get() = list

    override val callback: SubredditController.SubredditCallback?
        get() = object : SubredditController.SubredditCallback {

            override fun subscribeClick(index: Int) {}
        }

    override val controller: BaseController<ModeratedSub, SubredditController.SubredditCallback> = ModeratedSubsController(callback!!)

    override fun fetchItem(client: RedditClient?, query: String): Collection<ModeratedSub>? {

        return client?.redditorsClient?.moderatedSubreddits(query)
    }
}
