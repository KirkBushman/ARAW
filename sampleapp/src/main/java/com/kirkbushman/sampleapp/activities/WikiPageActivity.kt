package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.databinding.ActivityWikiBinding
import com.kirkbushman.sampleapp.util.DoAsync
import kotlin.Exception

class WikiPageActivity : BaseActivity() {

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

    private lateinit var binding: ActivityWikiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (subreddit == null || page == null) {

            binding.editSubreddit.visibility = View.VISIBLE
            binding.bttnSearch.visibility = View.VISIBLE

            binding.bttnSearch.setOnClickListener {

                val subreddit = binding.editSubreddit.text.toString().trim()
                if (subreddit.isNotEmpty()) {

                    var wiki: WikiPage? = null
                    var exception: Exception? = null
                    DoAsync(
                        doWork = {

                            try {
                                wiki = client?.wikisClient?.wiki(subreddit)
                            } catch (ex: Exception) {
                                exception = ex
                            }
                        },
                        onPost = {

                            binding.wikiText.text = wiki.toString()

                            if (exception != null) {
                                exception!!.printStackTrace()

                                binding.wikiText.text = exception!!.message
                            }
                        }
                    )
                }
            }
        }

        if (subreddit != null && page != null) {

            binding.editSubreddit.visibility = View.GONE
            binding.bttnSearch.visibility = View.GONE

            var wikiPage: WikiPage? = null
            DoAsync(
                doWork = {

                    wikiPage = client?.wikisClient?.wikiPage(subreddit!!, page!!)
                },
                onPost = {

                    binding.wikiText.text = wikiPage.toString()
                }
            )
        }
    }
}
