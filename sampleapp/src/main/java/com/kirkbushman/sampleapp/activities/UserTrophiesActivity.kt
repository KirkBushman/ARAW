package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.TrophiesController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_user_trophies.*

class UserTrophiesActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private val trophies = ArrayList<Trophy>()
    private val controller by lazy { TrophiesController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trophies)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val username = search.text.toString().trim()

            doAsync(doWork = {

                val temp = client?.account?.trophies(username)
                trophies.addAll(temp ?: listOf())
            }, onPost = {

                controller.setTrophies(trophies)
            })
        }
    }
}