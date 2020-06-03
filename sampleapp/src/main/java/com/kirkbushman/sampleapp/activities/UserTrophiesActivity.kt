package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.TrophiesController
import com.kirkbushman.sampleapp.util.doAsync
import kotlinx.android.synthetic.main.activity_user_trophies.*

class UserTrophiesActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, UserTrophiesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val trophies = ArrayList<Trophy>()
    private val controller by lazy { TrophiesController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trophies)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val username = search.text.toString().trim()

            doAsync(
                doWork = {

                    val temp = client?.redditorsClient?.trophies(username)

                    trophies.clear()
                    trophies.addAll(temp ?: listOf())
                },
                onPost = {
                    controller.setItems(trophies)
                }
            )
        }
    }
}
