package com.kirkbushman.sampleapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kirkbushman.sampleapp.fragments.ContributionFragment

class ContributionsAdapter(

    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    companion object {

        private val tags = arrayOf(
            ContributionFragment.TAG_OVERVIEW,
            ContributionFragment.TAG_SUBMITTED,
            ContributionFragment.TAG_COMMENTS,
            ContributionFragment.TAG_GILDED
        )
    }

    override fun createFragment(position: Int): Fragment {

        return ContributionFragment.newInstance(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun getTitleAtPos(position: Int): String {
        return tags[position]
    }

    fun getFragmentAtPos(position: Int): ContributionFragment? {
        return fragmentManager.findFragmentByTag("f$position") as? ContributionFragment?
    }

    fun doForEachFragment(action: (fragment: ContributionFragment?) -> Unit) {

        for (index in tags.indices) {

            val fragment = fragmentManager
                .findFragmentByTag("f$index") as? ContributionFragment?

            action(fragment)
        }
    }
}
