package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.fragments.SelfContributionFragment
import kotlinx.android.synthetic.main.activity_selfaccount_info.*

class SelfAccountInfoActivity : AppCompatActivity() {

    private val adapter by lazy { ContributionPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selfaccount_info)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        pager.adapter = adapter
        pager.offscreenPageLimit = 5

        tab_layout.setupWithViewPager(pager)

        with(adapter) {
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_OVERVIEW))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_SUBMITTED))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_COMMENTS))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_SAVED))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_UPVOTED))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_DOWNVOTED))
            addFragment(SelfContributionFragment.newInstance(SelfContributionFragment.TAG_GILDED))
            notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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

        (adapter.getItem(pager.currentItem) as SelfContributionFragment).reload(sorting, timePeriod)
    }

    private class ContributionPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

        val fragments = ArrayList<Fragment>()

        override fun getPageTitle(position: Int): CharSequence? = (fragments[position] as SelfContributionFragment).passedTag
        override fun getCount(): Int = fragments.size
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }
}