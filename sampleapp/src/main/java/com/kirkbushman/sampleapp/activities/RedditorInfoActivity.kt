package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.fragments.ContributionFragment
import kotlinx.android.synthetic.main.activity_redditor_info.*

class RedditorInfoActivity : AppCompatActivity() {

    private val adapter by lazy { ContributionPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redditor_info)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        pager.adapter = adapter
        pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(pager)

        with(adapter) {
            addFragment(ContributionFragment.newInstance(ContributionFragment.TAG_OVERVIEW))
            addFragment(ContributionFragment.newInstance(ContributionFragment.TAG_SUBMITTED))
            addFragment(ContributionFragment.newInstance(ContributionFragment.TAG_COMMENTS))
            addFragment(ContributionFragment.newInstance(ContributionFragment.TAG_GILDED))
            notifyDataSetChanged()
        }

        search_bttn.setOnClickListener {

            adapter.fragments.forEach {

                (it as ContributionFragment).refresh()
            }
        }
    }

    fun getUsername(): String {
        return search.text.toString().trim()
    }

    private class ContributionPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

        val fragments = ArrayList<Fragment>()

        override fun getPageTitle(position: Int): CharSequence? = (fragments[position] as ContributionFragment).passedTag
        override fun getCount(): Int = fragments.size
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }
}