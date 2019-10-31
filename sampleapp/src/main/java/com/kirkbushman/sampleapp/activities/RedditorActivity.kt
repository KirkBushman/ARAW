package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_redditor.*

class RedditorActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redditor)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        bttn_search.setOnClickListener {

            val username = edit_user.text.toString().trim()
            if (username.isNotEmpty()) {

                var redditor: Redditor? = null
                doAsync(doWork = {

                    redditor = client?.usersClient?.redditor(username)
                }, onPost = {

                    redditor_text.text = redditor.toString()
                })
            }
        }
    }
}
