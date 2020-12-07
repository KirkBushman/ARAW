package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.databinding.ActivityPrintBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import javax.inject.Inject

abstract class BasePrintActivity<T> : BaseActivity() {

    @Inject
    protected lateinit var client: RedditClient

    private lateinit var binding: ActivityPrintBinding

    abstract fun fetchItem(client: RedditClient): T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        var item: T? = null
        DoAsync(
            doWork = { item = fetchItem(client) },
            onPost = { binding.printText.text = item.toString() }
        )
    }
}
