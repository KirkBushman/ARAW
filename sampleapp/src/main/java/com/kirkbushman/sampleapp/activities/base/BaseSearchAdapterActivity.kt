package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kirkbushman.sampleapp.databinding.ActivitySearchAdapterBinding

abstract class BaseSearchAdapterActivity<T : FragmentStateAdapter> : BaseActivity() {

    abstract val adapter: T

    abstract fun getTitleAtPos(tab: TabLayout.Tab, position: Int)
    abstract fun reloadAllFragments()

    @StringRes
    open fun hintTextRes(): Int? {
        return null
    }

    protected lateinit var binding: ActivitySearchAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (hintTextRes() != null) {
            binding.search.hint = getString(hintTextRes()!!)
        }

        binding.pager.adapter = adapter

        val mediator = TabLayoutMediator(binding.tabLayout, binding.pager, ::getTitleAtPos)
        mediator.attach()

        binding.searchBttn.setOnClickListener {

            reloadAllFragments()
        }
    }
}
