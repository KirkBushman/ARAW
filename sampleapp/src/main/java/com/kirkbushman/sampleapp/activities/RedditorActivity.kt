package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity
import kotlinx.android.synthetic.main.activity_redditor.*

class RedditorActivity : BaseSearchPrintActivity<Redditor>(R.layout.activity_redditor) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, RedditorActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = bttn_search

    override val editSearch: EditText
        get() = edit_user

    override val textPrint: TextView
        get() = redditor_text

    override fun fetchItem(client: RedditClient?, query: String): Redditor? {

        return client?.redditorsClient?.redditor(query)
    }
}
