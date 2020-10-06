package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.BaseCallback
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.RedditorController
import kotlinx.android.synthetic.main.activity_user_search.*

class UserSearchActivity : BaseSearchControllerActivity2<Redditor>(R.layout.activity_user_search) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UserSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = search_bttn

    override val editSearch: EditText
        get() = query

    override val recyclerView: EpoxyRecyclerView
        get() = list

    override val controller: BaseController<Redditor, BaseCallback> = RedditorController()

    override fun fetchItem(client: RedditClient?, query: String): Collection<Redditor>? {

        val fetcher = client?.searchClient?.fetchRedditorSearch(query, showAll = true)
        return fetcher?.fetchNext()
    }
}
