package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity
import kotlinx.android.synthetic.main.activity_subreddit.*

class SubredditActivity : BaseSearchPrintActivity<Subreddit>(R.layout.activity_subreddit) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubredditActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = bttn_search

    override val editSearch: EditText
        get() = edit_subreddit

    override val textPrint: TextView
        get() = subreddit_text

    override fun fetchItem(client: RedditClient?, query: String): Subreddit? {
        return client?.subredditsClient?.subreddit(query)
    }
}
