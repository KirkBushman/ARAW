package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity2
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.controllers.RedditorController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity : BaseSearchControllerActivity2<RedditorData>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UserSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val controller: BaseController<RedditorData, BaseCallback> = RedditorController()

    override fun fetchItem(client: RedditClient, query: String): Collection<RedditorData> {

        val fetcher = client.searchClient.createRedditorSearchFetcher(query, showAll = true)
        return fetcher.fetchNext() ?: emptyList()
    }
}
