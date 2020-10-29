package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.fragments.SubmissionFragment
import kotlinx.android.synthetic.main.activity_inbox.*

class CommonSubmissionsActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, CommonSubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val adapter by lazy { SubmissionsPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions_common)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

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

    private fun reloadFragment(sorting: SubmissionsSorting? = null, timePeriod: TimePeriod? = null) {

        (adapter.getItem(pager.currentItem) as SubmissionFragment).reload(sorting, timePeriod)
    }

    private class SubmissionsPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
