package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.TrophiesController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_self_trophies.*
import kotlinx.android.synthetic.main.activity_self_trophies.list
import kotlinx.android.synthetic.main.fragment_inbox.*

class SelfTrophiesActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private val trophies = ArrayList<Trophy>()
    private val controller by lazy { TrophiesController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_trophies)

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(doWork = {

            val temp = client?.selfAccount?.trophies()
            trophies.addAll(temp ?: listOf())
        }, onPost = {

            controller.setTrophies(trophies)
        })
    }
}