package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrint2Activity
import kotlinx.android.synthetic.main.activity_multi_submissions.*

class MultiSubmissionsActivity : BaseSearchPrint2Activity<List<Submission>>(R.layout.activity_multi_submissions) {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, MultiSubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = bttn_search

    override val editSearch: EditText
        get() = edit_search

    override val editSearch2: EditText
        get() = edit_search_2

    override val textPrint: TextView
        get() = comment_text

    override fun fetchItem(client: RedditClient?, query: String, query2: String): List<Submission>? {

        val fetcher = client?.multisClient?.multiSubmissions(query, query2)
        return fetcher?.fetchNext()
    }
}
