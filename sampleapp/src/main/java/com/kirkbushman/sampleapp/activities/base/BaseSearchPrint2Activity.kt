package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.StringRes
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.databinding.ActivitySearchPrint2Binding
import com.kirkbushman.sampleapp.util.DoAsync

abstract class BaseSearchPrint2Activity<T> : BaseActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private lateinit var binding: ActivitySearchPrint2Binding

    @StringRes
    abstract fun hintTextRes(): Int
    @StringRes
    abstract fun hintTextRes2(): Int
    abstract fun fetchItem(client: RedditClient?, query: String, query2: String): T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchPrint2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.editSearch.hint = getString(hintTextRes())
        binding.editSearch2.hint = getString(hintTextRes2())

        binding.bttnSearch.setOnClickListener {

            val searchQuery = binding.editSearch.text.toString().trim()
            val searchQuery2 = binding.editSearch2.text.toString().trim()
            if (searchQuery.isNotEmpty() && searchQuery2.isNotEmpty()) {

                var item: T? = null
                DoAsync(
                    doWork = { item = fetchItem(client, searchQuery, searchQuery2) },
                    onPost = { binding.printText.text = item.toString() }
                )
            }
        }
    }
}
