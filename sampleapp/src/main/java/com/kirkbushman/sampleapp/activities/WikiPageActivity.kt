package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_wiki.*
import kotlin.Exception

class WikiPageActivity : AppCompatActivity() {

    companion object {

        private const val PARAM_SUBREDDIT_NAME = "intent_param_subreddit_name"
        private const val PARAM_PAGE_NAME = "intent_param_page_name"

        fun start(context: Context, subreddit: String? = null, page: String? = null) {

            val intent = Intent(context, WikiPageActivity::class.java)

            if (subreddit != null) {
                intent.putExtra(PARAM_SUBREDDIT_NAME, subreddit)
            }

            if (page != null) {
                intent.putExtra(PARAM_PAGE_NAME, page)
            }

            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val subreddit by lazy { intent.getStringExtra(PARAM_SUBREDDIT_NAME) }
    private val page by lazy { intent.getStringExtra(PARAM_PAGE_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (subreddit == null || page == null) {

            edit_subreddit.visibility = View.VISIBLE
            bttn_search.visibility = View.VISIBLE

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

        if (subreddit != null && page != null) {

            edit_subreddit.visibility = View.GONE
            bttn_search.visibility = View.GONE

            var wikiPage: WikiPage? = null
            doAsync(doWork = {

                wikiPage = client?.wikisClient?.wikiPage(subreddit, page)
            }, onPost = {

                wiki_text.text = wikiPage.toString()
            })
        }
    }
}
