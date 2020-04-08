package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : BaseSearchPrintActivity<Comment>(R.layout.activity_comment) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, CommentActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val bttnSearch: Button
        get() = bttn_search

    override val editSearch: EditText
        get() = edit_search

    override val textPrint: TextView
        get() = comment_text

    override fun fetchItem(client: RedditClient?, query: String): Comment? {
        return client?.contributionsClient?.comment(query)
    }
}
