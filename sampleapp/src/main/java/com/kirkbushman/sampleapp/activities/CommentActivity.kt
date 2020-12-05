package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchPrintActivity

class CommentActivity : BaseSearchPrintActivity<Comment>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, CommentActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun hintTextRes(): Int {
        return R.string.edit_insert_comment_id
    }

    override fun fetchItem(client: RedditClient?, query: String): Comment? {
        return client?.contributionsClient?.comment(query)
    }
}
