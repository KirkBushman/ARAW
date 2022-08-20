package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.StringRes
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.databinding.ActivitySearchPrintBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import javax.inject.Inject

abstract class BaseSearchPrintActivity<T> : BaseActivity() {

    @Inject
    protected lateinit var client: RedditClient

    private lateinit var binding: ActivitySearchPrintBinding

    @StringRes
    abstract fun hintTextRes(): Int
    abstract fun fetchItem(client: RedditClient, query: String): T?

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
