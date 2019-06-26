package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kirkbushman.araw.models.general.SubmissionSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.fragments.SubmissionFragment
import kotlinx.android.synthetic.main.activity_inbox.*

class CommonSubmissionsActivity : AppCompatActivity() {

    private val adapter by lazy { SubmissionsPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions_common)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        pager.adapter = adapter
        pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(pager)

        with(adapter) {
            addFragment(SubmissionFragment.newInstance(SubmissionFragment.TAG_FRONTPAGE))
            addFragment(SubmissionFragment.newInstance(SubmissionFragment.TAG_ALL))
            addFragment(SubmissionFragment.newInstance(SubmissionFragment.TAG_POPULAR))
            addFragment(SubmissionFragment.newInstance(SubmissionFragment.TAG_FRIENDS))
            notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sorting_time, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.item_sorting_hot -> { reloadFragment(sorting = SubmissionSorting.HOT); true }
            R.id.item_sorting_best -> { reloadFragment(sorting = SubmissionSorting.BEST); true }
            R.id.item_sorting_new -> { reloadFragment(sorting = SubmissionSorting.NEW); true }
            R.id.item_sorting_rising -> { reloadFragment(sorting = SubmissionSorting.RISING); true }
            R.id.item_sorting_controversial -> { reloadFragment(sorting = SubmissionSorting.CONTROVERSIAL); true }
            R.id.item_sorting_top -> { reloadFragment(sorting = SubmissionSorting.TOP); true }

            R.id.item_timeperiod_hour -> { reloadFragment(timePeriod = TimePeriod.LAST_HOUR); true }
            R.id.item_timeperiod_day -> { reloadFragment(timePeriod = TimePeriod.LAST_DAY); true }
            R.id.item_timeperiod_week -> { reloadFragment(timePeriod = TimePeriod.LAST_WEEK); true }
            R.id.item_timeperiod_month -> { reloadFragment(timePeriod = TimePeriod.LAST_MONTH); true }
            R.id.item_timeperiod_year -> { reloadFragment(timePeriod = TimePeriod.LAST_YEAR); true }
            R.id.item_timeperiod_all -> { reloadFragment(timePeriod = TimePeriod.ALL_TIME); true }

            else -> { false }
        }
    }

    private fun reloadFragment(sorting: SubmissionSorting? = null, timePeriod: TimePeriod? = null) {

        (adapter.getItem(pager.currentItem) as SubmissionFragment).reload(sorting, timePeriod)
    }

    private class SubmissionsPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

        private val fragments = ArrayList<Fragment>()

        override fun getPageTitle(position: Int): CharSequence? = (fragments[position] as SubmissionFragment).passedTag
        override fun getCount(): Int = fragments.size
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }
}