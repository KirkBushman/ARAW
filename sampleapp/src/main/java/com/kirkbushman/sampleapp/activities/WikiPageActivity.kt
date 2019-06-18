package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_wiki.*

class WikiPageActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        bttn_search.setOnClickListener {

            val subreddit = edit_subreddit.text.toString().trim()
            if (subreddit.isNotEmpty()) {

                var wiki: WikiPage? = null
                doAsync(doWork = {

                    wiki = client?.wiki(subreddit)
                }, onPost = {

                    wiki_text.text = wiki.toString()
                })
            }
        }
    }
}