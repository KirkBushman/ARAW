package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.ContributionSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.araw.models.mixins.Votable
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.ContributionController
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.fragment_inbox.*

class SelfContributionFragment : Fragment(R.layout.fragment_contribution) {

    companion object {

        private const val PASSED_TAG = "passed_tag_args"

        const val TAG_OVERVIEW = "overview"
        const val TAG_SUBMITTED = "submitted"
        const val TAG_COMMENTS = "comments"
        const val TAG_SAVED = "saved"
        const val TAG_UPVOTED = "upvoted"
        const val TAG_DOWNVOTED = "downvoted"
        const val TAG_GILDED = "gilded"

        fun newInstance(type: String): SelfContributionFragment {

            val fr = SelfContributionFragment()
            val args = Bundle()

            args.putString(PASSED_TAG, type)
            fr.arguments = args

            return fr
        }
    }

    val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    private val client by lazy { TestApplication.instance.getClient() }

    private val contributions = ArrayList<Contribution>()
    private val controller by lazy {
        ContributionController(object : SubmissionController.SubmissionCallback {

            override fun onUpvoteClick(index: Int) {

                doAsync(doWork = {
                    val votable = contributions[index] as Votable
                    client?.contributions?.vote(Vote.UPVOTE, votable)
                })
            }

            override fun onNoneClick(index: Int) {

                doAsync(doWork = {
                    val votable = contributions[index] as Votable
                    client?.contributions?.vote(Vote.NONE, votable)
                })
            }

            override fun onDownClick(index: Int) {

                doAsync(doWork = {
                    val votable = contributions[index] as Votable
                    client?.contributions?.vote(Vote.DOWNVOTE, votable)
                })
            }

            override fun onSaveClick(index: Int) {

                doAsync(doWork = {
                    val contribution = contributions[index]
                    val saved = when (contribution) {
                        is Submission -> (contribution).isSaved
                        is Comment -> (contribution).isSaved

                        else -> false
                    }

                    client?.contributions?.save(!saved, contribution)
                })
            }
        })
    }

    private var fetcher: ContributionsFetcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.setController(controller)

        doAsync(doWork = {

            fetcher = getFetcher()
            contributions.addAll(fetcher?.fetchNext() ?: listOf())
        }, onPost = {

            controller.setContributions(contributions)
        })
    }

    fun reload(sorting: ContributionSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            doAsync(doWork = {

                fetcher!!.setSorting(sorting)

                contributions.clear()
                contributions.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {
                controller.setContributions(contributions)
            })
        }

        if (timePeriod != null) {

            doAsync(doWork = {

                fetcher!!.setTimePeriod(timePeriod)

                contributions.clear()
                contributions.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {
                controller.setContributions(contributions)
            })
        }
    }

    private fun getFetcher(): ContributionsFetcher? {

        return when (passedTag) {
            TAG_OVERVIEW -> client?.selfAccount?.overview()
            TAG_SUBMITTED -> client?.selfAccount?.submitted()
            TAG_COMMENTS -> client?.selfAccount?.comments()
            TAG_SAVED -> client?.selfAccount?.saved()
            TAG_UPVOTED -> client?.selfAccount?.upvoted()
            TAG_DOWNVOTED -> client?.selfAccount?.downvoted()
            TAG_GILDED -> client?.selfAccount?.gilded()

            else -> null
        }
    }
}