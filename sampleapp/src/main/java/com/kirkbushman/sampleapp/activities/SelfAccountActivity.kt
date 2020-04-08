package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Me
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BasePrintActivity
import kotlinx.android.synthetic.main.activity_selfaccount.*

class SelfAccountActivity : BasePrintActivity<Me>(R.layout.activity_selfaccount) {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfAccountActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val actionBar: Toolbar
        get() = toolbar

    override val textPrint: TextView
        get() = self_account

    override fun fetchItem(client: RedditClient?): Me? {

        return client?.accountsClient?.me()
    }
}
