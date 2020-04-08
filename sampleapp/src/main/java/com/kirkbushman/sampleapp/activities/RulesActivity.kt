package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.BaseCallback
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.RulesController
import kotlinx.android.synthetic.main.activity_rules.*

class RulesActivity : BaseSearchControllerActivity2<SubredditRule>(R.layout.activity_rules) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RulesActivity::class.java)
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

    override val controller: BaseController<SubredditRule, BaseCallback> = RulesController()

    override fun fetchItem(client: RedditClient?, query: String): Collection<SubredditRule>? {

        return client?.subredditsClient?.rules(query)?.toList()
    }
}
