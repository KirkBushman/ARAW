package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.fragments.InboxFragment
import com.kirkbushman.sampleapp.fragments.InboxFragment.Companion.TAG_INBOX
import com.kirkbushman.sampleapp.fragments.InboxFragment.Companion.TAG_MENTIONS
import com.kirkbushman.sampleapp.fragments.InboxFragment.Companion.TAG_MESSAGES
import com.kirkbushman.sampleapp.fragments.InboxFragment.Companion.TAG_SENT
import com.kirkbushman.sampleapp.fragments.InboxFragment.Companion.TAG_UNREAD
import kotlinx.android.synthetic.main.activity_inbox.*

class InboxActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, InboxActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val adapter by lazy { InboxPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        pager.adapter = adapter
        pager.offscreenPageLimit = 5

        tab_layout.setupWithViewPager(pager)

        with(adapter) {
            addFragment(InboxFragment.newInstance(TAG_INBOX))
            addFragment(InboxFragment.newInstance(TAG_UNREAD))
            addFragment(InboxFragment.newInstance(TAG_MESSAGES))
            addFragment(InboxFragment.newInstance(TAG_SENT))
            addFragment(InboxFragment.newInstance(TAG_MENTIONS))
            notifyDataSetChanged()
        }
    }

    private class InboxPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments = ArrayList<Fragment>()

        override fun getPageTitle(position: Int): CharSequence? = (fragments[position] as InboxFragment).passedTag
        override fun getCount(): Int = fragments.size
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }
}
