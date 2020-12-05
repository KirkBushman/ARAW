package com.kirkbushman.sampleapp.activities.base

import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kirkbushman.sampleapp.databinding.ActivityAdapterBinding

abstract class BaseAdapterActivity<T : FragmentStateAdapter> : BaseActivity() {

    abstract val adapter: T
    abstract fun getTitleAtPos(tab: TabLayout.Tab, position: Int)

    protected lateinit var binding: ActivityAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.pager.adapter = adapter

        val mediator = TabLayoutMediator(binding.tabLayout, binding.pager, ::getTitleAtPos)
        mediator.attach()
    }
}
