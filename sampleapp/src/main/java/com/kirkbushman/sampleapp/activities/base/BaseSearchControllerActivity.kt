package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.StringRes
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.databinding.ActivitySearchControllerBinding
import com.kirkbushman.sampleapp.util.DoAsync

abstract class BaseSearchControllerActivity2<T> : BaseSearchControllerActivity<T, BaseCallback>() {

    override val callback: BaseCallback?
        get() = null
}

abstract class BaseSearchControllerActivity<T, C : BaseCallback> : BaseActivity() {

    protected val client by lazy { TestApplication.instance.getClient() }

    protected val items = ArrayList<T>()

    abstract val callback: C?
    abstract val controller: BaseController<T, C>

    @StringRes
    open fun hintTextRes(): Int? {
        return null
    }

    private lateinit var binding: ActivitySearchControllerBinding

    abstract fun fetchItem(client: RedditClient?, query: String): Collection<T>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (hintTextRes() != null) {
            binding.editSearch.hint = getString(hintTextRes()!!)
        }

        binding.list.setHasFixedSize(true)
        binding.list.setController(controller)

        binding.searchBttn.setOnClickListener {

            val searchQuery = binding.editSearch.text.toString().trim()

            DoAsync(
                doWork = {

                    val newItems = fetchItem(client, searchQuery)

                    items.clear()
                    items.addAll(newItems ?: emptyList())
                },
                onPost = {

                    controller.setItems(items)
                }
            )
        }
    }
}
