package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.RulesController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_rules.*
import kotlinx.android.synthetic.main.activity_rules.list
import kotlinx.android.synthetic.main.fragment_inbox.*

class RulesActivity : AppCompatActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private val rules = ArrayList<SubredditRule>()
    private val controller by lazy { RulesController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val subreddit = search.text.toString().trim()

            doAsync(doWork = {

                val newRules = client?.rules(subreddit)
                rules.addAll(newRules ?: arrayOf())
            }, onPost = {

                controller.setRules(rules)
            })
        }
    }
}