package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.base.SubredditData
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubredditSearchController
import com.kirkbushman.sampleapp.databinding.ActivitySubredditsSearchBinding
import com.kirkbushman.sampleapp.util.DoAsync

class SubredditsSearchActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubredditsSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private var searchResult: SubredditSearchResult? = null
    private val data = ArrayList<SubredditData>()
    private val controller by lazy {

        SubredditSearchController(

            object : SubredditSearchController.SubredditCallback {

                override fun subscribeClick(index: Int) {

                    val subreddit = data[index]
                    DoAsync(
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
        )
    }

    private lateinit var binding: ActivitySubredditsSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubredditsSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.list.setHasFixedSize(true)
        binding.list.setController(controller)

        binding.searchBttn.setOnClickListener {

            val query = binding.query.text.toString().trim()

            if (!binding.startsWith.isChecked) {

                DoAsync(
                    doWork = {

                        val fetcher = client?.searchClient?.fetchSubredditsSearch(query)

                        data.clear()
                        data.addAll(fetcher?.fetchNext() ?: listOf())
                    },
                    onPost = {

                        controller.setSubreddits(data)
                    }
                )
            } else {

                DoAsync(
                    doWork = {

                        searchResult = client?.searchClient?.searchSubreddits(
                            query = query,
                            includeOver18 = true
                        )
                    },
                    onPost = {

                        if (searchResult != null) {
                            controller.setSearchResult(searchResult!!)
                        }
                    }
                )
            }
        }
    }

    private fun refresh() {
        controller.setSubreddits(data)
    }
}
