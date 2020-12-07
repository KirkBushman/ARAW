package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.base.SubredditData
import com.kirkbushman.sampleapp.activities.base.BaseControllerActivity
import com.kirkbushman.sampleapp.controllers.SubredditController
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscribedSubredditsActivity : BaseControllerActivity<SubredditData, SubredditController.SubredditCallback>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubscribedSubredditsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val callback = object : SubredditController.SubredditCallback {

        override fun subscribeClick(index: Int) {

            val subreddit = items[index]
            DoAsync(
                doWork = {

                    if (subreddit is Subreddit) {
                        client.subredditsClient.subscribe(subreddit)
                    }
                },
                onPost = {

                    if (subreddit is Subreddit && subreddit.isSubscriber != null) {

                        items[index] = subreddit.copy(isSubscriber = !subreddit.isSubscriber!!)
                        controller.setItems(items)
                    }
                }
            )
        }
    }

    override val controller: SubredditController by lazy { SubredditController(callback) }

    override fun fetchItem(client: RedditClient): Collection<SubredditData> {

        val fetcher = client.accountsClient.subscribedSubreddits(limit = 100)
        return fetcher.fetchNext()
    }
}
