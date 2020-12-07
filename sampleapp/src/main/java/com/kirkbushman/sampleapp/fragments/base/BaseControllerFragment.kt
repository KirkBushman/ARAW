package com.kirkbushman.sampleapp.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.databinding.FragmentEpoxyBinding
import com.kirkbushman.sampleapp.utils.DoAsync
import javax.inject.Inject

abstract class BaseControllerFragment<T, C : BaseCallback> : Fragment() {

    @Inject
    protected lateinit var client: RedditClient

    protected val items = ArrayList<T>()

    private var binding: FragmentEpoxyBinding? = null

    abstract val callback: C?
    abstract val controller: BaseController<T, C>

    abstract fun fetchItem(client: RedditClient?): Collection<T>?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEpoxyBinding.inflate(layoutInflater)
        binding!!.list.setHasFixedSize(true)
        binding!!.list.setController(controller)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
