package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.controllers.RedditorController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity : BaseSearchControllerActivity2<Redditor>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UserSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val controller: BaseController<Redditor, BaseCallback> = RedditorController()

    override fun fetchItem(client: RedditClient, query: String): Collection<Redditor> {

        val fetcher = client.searchClient.fetchRedditorSearch(query, showAll = true)
        return fetcher.fetchNext()
    }
}
