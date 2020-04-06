package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.RedditorController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_user_search.*

class UserSearchActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val redditors = ArrayList<Redditor>()
    private val controller by lazy { RedditorController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val query = query.text.toString().trim()

            doAsync(doWork = {

                val fetcher = client?.redditorsClient?.fetchRedditorSearch(query, show = "all")

                redditors.clear()
                redditors.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {

                controller.setRedditors(redditors)
            })
        }
    }
}
