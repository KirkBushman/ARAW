package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.BaseCallback
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.util.doAsync

abstract class BaseControllerActivity2<T>(@LayoutRes contentLayoutId: Int) : BaseControllerActivity<T, BaseCallback>(contentLayoutId) {

    override val callback: BaseCallback?
        get() = null
}

abstract class BaseControllerActivity<T, C : BaseCallback>(@LayoutRes contentLayoutId: Int) : BaseActivity(contentLayoutId) {

    private val client by lazy { TestApplication.instance.getClient() }

    private val items = ArrayList<T>()

    abstract val callback: C?
    abstract val controller: BaseController<T, C>

    abstract val actionBar: Toolbar
    abstract val recyclerView: EpoxyRecyclerView

    abstract fun fetchItem(client: RedditClient?): Collection<T>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(actionBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.setController(controller)

        doAsync(doWork = {

            val newItems = fetchItem(client)

            items.clear()
            items.addAll(newItems ?: emptyList())
        }, onPost = {

            controller.setItems(items)
        })
    }
}
