package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RedditorActivity : BaseSearchPrintActivity<RedditorData>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RedditorActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.edit_insert_user
    }

    override fun fetchItem(client: RedditClient, query: String): RedditorData? {

        return client.redditorsClient.redditor(query)
    }
}
