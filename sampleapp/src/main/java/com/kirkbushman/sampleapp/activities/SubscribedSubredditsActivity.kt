package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.mixins.SubredditData
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubredditController
import com.kirkbushman.sampleapp.util.doAsync
import kotlinx.android.synthetic.main.activity_mine_subreddits.*

class SubscribedSubredditsActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubscribedSubredditsActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }
    private val fetcher by lazy { client?.accountsClient?.subscribedSubreddits(limit = 100) }

    private val data = ArrayList<SubredditData>()
    private val controller by lazy { SubredditController(callback) }

    private val callback = object : SubredditController.SubredditCallback {

        override fun subscribeClick(index: Int) {

            val subreddit = data[index]
            doAsync(
                doWork = {

                    if (subreddit is Subreddit) {
                        client?.subredditsClient?.subscribe(subreddit)
                    }
                },
                onPost = {

                    if (subreddit is Subreddit && subreddit.isSubscriber != null) {

                        data[index] = subreddit.copy(isSubscriber = !subreddit.isSubscriber!!)
                        refresh()
                    }
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_subreddits)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(
            doWork = {

                val temp = fetcher?.fetchNext() ?: listOf()

                data.clear()
                data.addAll(temp)
            },
            onPost = {

                refresh()
            }
        )
    }

    private fun refresh() {
        controller.setSubreddits(data)
    }
}
