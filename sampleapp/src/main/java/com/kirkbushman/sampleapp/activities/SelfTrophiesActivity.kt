package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.TrophiesController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_self_trophies.*

class SelfTrophiesActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfTrophiesActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val trophies = ArrayList<Trophy>()
    private val controller by lazy { TrophiesController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_trophies)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(doWork = {

            val temp = client?.accountsClient?.myTrophies()

            trophies.clear()
            trophies.addAll(temp ?: listOf())
        }, onPost = {

            controller.setTrophies(trophies)
        })
    }
}
