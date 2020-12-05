package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.databinding.ActivityPrintBinding
import com.kirkbushman.sampleapp.util.DoAsync

abstract class BasePrintActivity<T> : BaseActivity() {

    private val client by lazy { TestApplication.instance.getClient() }

    private lateinit var binding: ActivityPrintBinding

    abstract fun fetchItem(client: RedditClient?): T?

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
