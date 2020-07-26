package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.util.doAsync

abstract class BaseSearchPrint2Activity<T>(@LayoutRes contentLayoutId: Int) : BaseActivity(contentLayoutId) {

    private val client by lazy { TestApplication.instance.getClient() }

    abstract val actionBar: Toolbar
    abstract val bttnSearch: Button
    abstract val editSearch: EditText
    abstract val editSearch2: EditText
    abstract val textPrint: TextView

    abstract fun fetchItem(client: RedditClient?, query: String, query2: String): T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(actionBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        bttnSearch.setOnClickListener {

            val searchQuery = editSearch.text.toString().trim()
            val searchQuery2 = editSearch2.text.toString().trim()
            if (searchQuery.isNotEmpty() && searchQuery2.isNotEmpty()) {

                var item: T? = null
                doAsync(
                    doWork = { item = fetchItem(client, searchQuery, searchQuery2) },
                    onPost = { textPrint.text = item.toString() }
                )
            }
        }
    }
}
