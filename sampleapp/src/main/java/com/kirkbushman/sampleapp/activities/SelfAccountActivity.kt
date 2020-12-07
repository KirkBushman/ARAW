package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Me
import com.kirkbushman.sampleapp.activities.base.BasePrintActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelfAccountActivity : BasePrintActivity<Me>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfAccountActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun fetchItem(client: RedditClient): Me? {

        return client.accountsClient.me()
    }
}
