package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.WikiPagesController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_wiki_pages.*

class WikiPagesActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, WikiPagesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private var subreddit: String? = null

    private val wikiPages = ArrayList<String>()
    private val controller by lazy {

        WikiPagesController(object : WikiPagesController.WikiPageCallback {

            override fun onPageClick(index: Int) {

                val item = wikiPages[index]
                WikiPageActivity.start(this@WikiPagesActivity, subreddit ?: "", item)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki_pages)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            subreddit = search.text.toString().trim()

            doAsync(doWork = {

                wikiPages.clear()
                wikiPages.addAll(client?.wikisClient?.wikiPages(subreddit ?: "") ?: emptyList())
            }, onPost = {

                controller.setWikiPages(wikiPages)
            })
        }
    }
}
