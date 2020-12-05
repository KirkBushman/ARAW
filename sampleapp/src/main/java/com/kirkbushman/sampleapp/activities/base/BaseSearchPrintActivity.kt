package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.StringRes
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.databinding.ActivitySearchPrintBinding
import com.kirkbushman.sampleapp.util.DoAsync

abstract class BaseSearchPrintActivity<T> : BaseActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private lateinit var binding: ActivitySearchPrintBinding

    @StringRes
    abstract fun hintTextRes(): Int
    abstract fun fetchItem(client: RedditClient?, query: String): T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.editSearch.hint = getString(hintTextRes())

        binding.bttnSearch.setOnClickListener {

            val searchQuery = binding.editSearch.text.toString().trim()
            if (searchQuery.isNotEmpty()) {

                var item: T? = null
                DoAsync(
                    doWork = { item = fetchItem(client, searchQuery) },
                    onPost = { binding.printText.text = item.toString() }
                )
            }
        }
    }
}
