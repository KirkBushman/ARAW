package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SearchSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrint2Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmissionsFlairFilterActivity : BaseSearchPrint2Activity<List<Submission>>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionsFlairFilterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.bttn_subreddit_search
    }

    override fun hintTextRes2(): Int {
        return R.string.edit_insert_text_or_link
    }

    override fun fetchItem(client: RedditClient, query: String, query2: String): List<Submission> {

        val fetcher = client
            .searchClient
            .submissionsSearch(
                subreddit = query,
                query = "flair_name:\"$query2\"",
                sorting = SearchSorting.NEW,
                timePeriod = TimePeriod.ALL_TIME,
                restrictToSubreddit = true
            )

        return fetcher.fetchNext() ?: emptyList()
    }
}
