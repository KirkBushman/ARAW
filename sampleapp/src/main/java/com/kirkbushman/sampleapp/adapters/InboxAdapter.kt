package com.kirkbushman.sampleapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kirkbushman.sampleapp.fragments.InboxFragment

class InboxAdapter(

    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    companion object {

        private val tags = arrayOf(
            InboxFragment.TAG_INBOX,
            InboxFragment.TAG_UNREAD,
            InboxFragment.TAG_MESSAGES,
            InboxFragment.TAG_SENT,
            InboxFragment.TAG_MENTIONS
        )
    }

    override fun createFragment(position: Int): Fragment {

        return InboxFragment.newInstance(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun getTitleAtPos(position: Int): String {
        return tags[position]
    }
}
