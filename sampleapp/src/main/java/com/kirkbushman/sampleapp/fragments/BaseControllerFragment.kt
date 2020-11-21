package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.BaseCallback
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.util.DoAsync

abstract class BaseControllerFragment<T, C : BaseCallback>(
    @LayoutRes contentLayoutId: Int
) : Fragment(
    contentLayoutId
) {

    protected val client by lazy { TestApplication.instance.getClient() }

    protected val items = ArrayList<T>()

    abstract val callback: C?
    abstract val controller: BaseController<T, C>

    abstract val recyclerView: EpoxyRecyclerView

    abstract fun fetchItem(client: RedditClient?): Collection<T>?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.setHasFixedSize(true)
        recyclerView.setController(controller)

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
