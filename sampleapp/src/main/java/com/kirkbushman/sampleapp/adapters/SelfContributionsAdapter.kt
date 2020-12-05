package com.kirkbushman.sampleapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kirkbushman.sampleapp.fragments.SelfContributionFragment

class SelfContributionsAdapter(

    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    companion object {

        private val tags = arrayOf(
            SelfContributionFragment.TAG_OVERVIEW,
            SelfContributionFragment.TAG_SUBMITTED,
            SelfContributionFragment.TAG_COMMENTS,
            SelfContributionFragment.TAG_SAVED,
            SelfContributionFragment.TAG_UPVOTED,
            SelfContributionFragment.TAG_DOWNVOTED,
            SelfContributionFragment.TAG_GILDED
        )
    }

    override fun createFragment(position: Int): Fragment {

        return SelfContributionFragment.newInstance(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun getTitleAtPos(position: Int): String {
        return tags[position]
    }

    fun getFragmentAtPos(position: Int): SelfContributionFragment? {
        return fragmentManager.findFragmentByTag("f$position") as? SelfContributionFragment?
    }
}
