package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubredditController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_subreddits_search.*

class SubredditsSearchActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubredditsSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private var searchResult: SubredditSearchResult? = null
    private val subreddits = ArrayList<Subreddit>()
    private val controller by lazy {
        SubredditController(object : SubredditController.SubredditCallback {

            override fun subscribeClick(index: Int) {

                val subreddit = subreddits[index]
                doAsync(doWork = {

                    client?.subredditsClient?.subscribe(subreddit)
                }, onPost = {

                    if (subreddit.isSubscriber != null) {

                        subreddits[index] = subreddit.copy(isSubscriber = !subreddit.isSubscriber!!)
                        refresh()
                    }
                })
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subreddits_search)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val query = query.text.toString().trim()

            if (!starts_with.isChecked) {

                doAsync(doWork = {

                    val fetcher = client?.subredditsClient?.fetchSubredditsSearch(query)

                    subreddits.clear()
                    subreddits.addAll(fetcher?.fetchNext() ?: listOf())
                }, onPost = {

                    controller.setSubreddits(subreddits)
                })
            } else {

                doAsync(doWork = {

                    searchResult = client?.searchSubreddits(
                        query = query,
                        includeOver18 = true
                    )
                }, onPost = {

                    if (searchResult != null) {
                        controller.setSearchResult(searchResult!!)
                    }
                })
            }
        }
    }

    private fun refresh() {
        controller.setSubreddits(subreddits)
    }
}
