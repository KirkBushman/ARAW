package com.kirkbushman.sampleapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kirkbushman.sampleapp.fragments.SubmissionFragment

class SubmissionsAdapter(

    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    companion object {

        private val tags = arrayOf(
            SubmissionFragment.TAG_FRONTPAGE,
            SubmissionFragment.TAG_ALL,
            SubmissionFragment.TAG_POPULAR,
            SubmissionFragment.TAG_FRIENDS
        )
    }

    override fun createFragment(position: Int): Fragment {

        return SubmissionFragment.newInstance(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun getTitleAtPos(position: Int): String {
        return tags[position]
    }

    fun getFragmentAtPos(position: Int): SubmissionFragment? {
        return fragmentManager.findFragmentByTag("f$position") as? SubmissionFragment?
    }
}
