package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.SubredditController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_mine_subreddits.*
import kotlinx.android.synthetic.main.activity_mine_subreddits.list
import kotlinx.android.synthetic.main.fragment_inbox.*

class SubscribedSubredditsActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }
    private val fetcher by lazy { client?.selfAccount?.subscribedSubreddits(limit = 100) }

    private val subreddits = ArrayList<Subreddit>()
    private val controller by lazy { SubredditController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_subreddits)

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(doWork = {

            val temp = fetcher?.fetchNext() ?: listOf()
            subreddits.addAll(temp)
        }, onPost = {

            controller.setSubreddits(subreddits)
        })
    }
}