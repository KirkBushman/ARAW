package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_wiki.*
import kotlin.Exception

class WikiPageActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        bttn_search.setOnClickListener {

            val subreddit = edit_subreddit.text.toString().trim()
            if (subreddit.isNotEmpty()) {

                var wiki: WikiPage? = null
                var exception: Exception? = null
                doAsync(doWork = {

                    try {
                        wiki = client?.wikisClient?.wiki(subreddit)
                    } catch (ex: Exception) {
                        exception = ex
                    }
                }, onPost = {

                    wiki_text.text = wiki.toString()

                    if (exception != null) {
                        exception!!.printStackTrace()

                        wiki_text.text = exception!!.message
                    }
                })
            }
        }
    }
}
