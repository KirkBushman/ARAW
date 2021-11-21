package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseAdapterActivity
import com.kirkbushman.sampleapp.adapters.SelfContributionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelfAccountInfoActivity : BaseAdapterActivity<SelfContributionsAdapter>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SelfAccountInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val adapter by lazy {

        SelfContributionsAdapter(supportFragmentManager, lifecycle)
    }

    override fun getTitleAtPos(tab: TabLayout.Tab, position: Int) {

        tab.text = adapter.getTitleAtPos(position)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sorting_time_contrib, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.item_sorting_hot -> { reloadFragment(sorting = ContributionsSorting.HOT); true }
            R.id.item_sorting_new -> { reloadFragment(sorting = ContributionsSorting.NEW); true }
            R.id.item_sorting_controversial -> { reloadFragment(sorting = ContributionsSorting.CONTROVERSIAL); true }
            R.id.item_sorting_top -> { reloadFragment(sorting = ContributionsSorting.TOP); true }

            R.id.item_timeperiod_hour -> { reloadFragment(timePeriod = TimePeriod.LAST_HOUR); true }
            R.id.item_timeperiod_day -> { reloadFragment(timePeriod = TimePeriod.LAST_DAY); true }
            R.id.item_timeperiod_week -> { reloadFragment(timePeriod = TimePeriod.LAST_WEEK); true }
            R.id.item_timeperiod_month -> { reloadFragment(timePeriod = TimePeriod.LAST_MONTH); true }
            R.id.item_timeperiod_year -> { reloadFragment(timePeriod = TimePeriod.LAST_YEAR); true }
            R.id.item_timeperiod_all -> { reloadFragment(timePeriod = TimePeriod.ALL_TIME); true }

            else -> { false }
        }
    }

    private fun reloadFragment(sorting: ContributionsSorting? = null, timePeriod: TimePeriod? = null) {

        adapter.getFragmentAtPos(binding.pager.currentItem)?.reload(sorting, timePeriod)
    }
}
