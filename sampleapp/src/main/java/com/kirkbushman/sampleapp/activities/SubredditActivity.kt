package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_subreddit.*

class SubredditActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subreddit)

        bttn_search.setOnClickListener {

            val subredditName = edit_subreddit.text.toString().trim()
            if (subredditName.isNotEmpty()) {

                var subreddit: Subreddit? = null
                doAsync(doWork = {

                    subreddit = client?.subreddit(subredditName)
                }, onPost = {

                    subreddit_text.text = subreddit.toString()
                })
            }
        }
    }
}
