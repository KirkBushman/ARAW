package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseAdapterActivity
import com.kirkbushman.sampleapp.adapters.SubmissionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommonSubmissionsActivity : BaseAdapterActivity<SubmissionsAdapter>() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, CommonSubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val adapter by lazy {

        SubmissionsAdapter(supportFragmentManager, lifecycle)
    }

    override fun getTitleAtPos(tab: TabLayout.Tab, position: Int) {

        tab.text = adapter.getTitleAtPos(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sorting_time, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.item_sorting_hot -> { reloadFragment(sorting = SubmissionsSorting.HOT); true }
            R.id.item_sorting_best -> { reloadFragment(sorting = SubmissionsSorting.BEST); true }
            R.id.item_sorting_new -> { reloadFragment(sorting = SubmissionsSorting.NEW); true }
            R.id.item_sorting_rising -> { reloadFragment(sorting = SubmissionsSorting.RISING); true }
            R.id.item_sorting_controversial -> { reloadFragment(sorting = SubmissionsSorting.CONTROVERSIAL); true }
            R.id.item_sorting_top -> { reloadFragment(sorting = SubmissionsSorting.TOP); true }

            R.id.item_timeperiod_hour -> { reloadFragment(timePeriod = TimePeriod.LAST_HOUR); true }
            R.id.item_timeperiod_day -> { reloadFragment(timePeriod = TimePeriod.LAST_DAY); true }
            R.id.item_timeperiod_week -> { reloadFragment(timePeriod = TimePeriod.LAST_WEEK); true }
            R.id.item_timeperiod_month -> { reloadFragment(timePeriod = TimePeriod.LAST_MONTH); true }
            R.id.item_timeperiod_year -> { reloadFragment(timePeriod = TimePeriod.LAST_YEAR); true }
            R.id.item_timeperiod_all -> { reloadFragment(timePeriod = TimePeriod.ALL_TIME); true }

            else -> { false }
        }
    }

    private fun reloadFragment(
        sorting: SubmissionsSorting? = null,
        timePeriod: TimePeriod? = null
    ) {
        adapter.getFragmentAtPos(binding.pager.currentItem)?.reload(sorting, timePeriod)
    }
}
