package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.databinding.ActivityControllerBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import javax.inject.Inject

abstract class BaseControllerActivity2<T> : BaseControllerActivity<T, BaseCallback>() {

    override val callback: BaseCallback?
        get() = null
}

abstract class BaseControllerActivity<T, C : BaseCallback> : BaseActivity() {

    @Inject
    protected lateinit var client: RedditClient

    protected val items = ArrayList<T>()

    abstract val callback: C?
    abstract val controller: BaseController<T, C>

    private lateinit var binding: ActivityControllerBinding

    abstract fun fetchItem(client: RedditClient): Collection<T>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.list.setHasFixedSize(true)
        binding.list.setController(controller)

        DoAsync(
            doWork = {

                val newItems = fetchItem(client)

                items.clear()
                items.addAll(newItems ?: emptyList())
            },
            onPost = {
                controller.setItems(items)
            }
        )
    }
}
