package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.util.doAsync

abstract class BasePrintActivity<T>(@LayoutRes contentLayoutId: Int) : BaseActivity(contentLayoutId) {

    private val client by lazy { TestApplication.instance.getClient() }

    abstract val actionBar: Toolbar
    abstract val textPrint: TextView

    abstract fun fetchItem(client: RedditClient?): T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(actionBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        var item: T? = null
        doAsync(
            doWork = { item = fetchItem(client) },
            onPost = { textPrint.text = item.toString() }
        )
    }
}
