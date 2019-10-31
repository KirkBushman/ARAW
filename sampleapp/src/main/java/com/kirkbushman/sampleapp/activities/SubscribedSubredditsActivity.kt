package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.SubredditController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_mine_subreddits.*

class SubscribedSubredditsActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }
    private val fetcher by lazy { client?.accountClient?.subscribedSubreddits(limit = 100) }

    private val subreddits = ArrayList<Subreddit>()
    private val controller by lazy { SubredditController(callback) }

    private val callback = object : SubredditController.SubredditCallback {

        override fun subscribeClick(index: Int) {

            val subreddit = subreddits[index]
            doAsync(doWork = {

                client?.subredditClient?.subscribe(subreddit)
            }, onPost = {

                subreddits[index] = subreddit.copy(isSubscriber = !subreddit.isSubscriber)
                refresh()
            })
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

        doAsync(doWork = {

            val temp = fetcher?.fetchNext() ?: listOf()

            subreddits.clear()
            subreddits.addAll(temp)
        }, onPost = {

            refresh()
        })
    }

    private fun refresh() {
        controller.setSubreddits(subreddits)
    }
}
