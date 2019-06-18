package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
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