package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import com.google.android.material.tabs.TabLayout
import com.kirkbushman.sampleapp.activities.base.BaseAdapterActivity
import com.kirkbushman.sampleapp.adapters.InboxAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxActivity : BaseAdapterActivity<InboxAdapter>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, InboxActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val adapter by lazy {

        InboxAdapter(supportFragmentManager, lifecycle)
    }

    override fun getTitleAtPos(tab: TabLayout.Tab, position: Int) {

        tab.text = adapter.getTitleAtPos(position)
    }
}
