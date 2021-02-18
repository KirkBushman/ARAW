package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrint2Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MultiSubmissionsActivity : BaseSearchPrint2Activity<List<Submission>>() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MultiSubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.bttn_user_search
    }

    override fun hintTextRes2(): Int {
        return R.string.edit_insert_text_or_link
    }

    override fun fetchItem(client: RedditClient, query: String, query2: String): List<Submission> {

        val fetcher = client.multisClient.multiSubmissions(query, query2)
        return fetcher.fetchNext() ?: emptyList()
    }
}
