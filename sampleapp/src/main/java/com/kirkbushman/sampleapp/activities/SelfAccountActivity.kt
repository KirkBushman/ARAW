package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Me
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_selfaccount.*

class SelfAccountActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selfaccount)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        var me: Me? = null
        doAsync(doWork = {

            me = client?.accountClient?.me()
        }, onPost = {

            self_account.text = me.toString()
        })
    }
}
