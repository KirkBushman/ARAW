package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.araw.models.base.Contribution
import com.kirkbushman.araw.models.base.Votable
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.ContributionController
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.util.doAsync
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

        ContributionController(

            object : SubmissionController.SubmissionCallback {

                override fun onUpvoteClick(index: Int) {

                    doAsync(
                        doWork = {
                            val votable = contributions[index] as Votable
                            client?.contributionsClient?.vote(Vote.UPVOTE, votable)
                        }
                    )
                }

                override fun onNoneClick(index: Int) {

                    doAsync(
                        doWork = {
                            val votable = contributions[index] as Votable
                            client?.contributionsClient?.vote(Vote.NONE, votable)
                        }
                    )
                }

                override fun onDownClick(index: Int) {

                    doAsync(
                        doWork = {
                            val votable = contributions[index] as Votable
                            client?.contributionsClient?.vote(Vote.DOWNVOTE, votable)
                        }
                    )
                }

                override fun onSaveClick(index: Int) {

                    doAsync(
                        doWork = {
                            when (val contribution = contributions[index]) {
                                is Submission -> client?.contributionsClient?.save(!contribution.isSaved, contribution)
                                is Comment -> client?.contributionsClient?.save(!contribution.isSaved, contribution)

                                else -> {}
                            }
                        }
                    )
                }

                override fun onHideClick(index: Int) {}
                override fun onLockClick(index: Int) {}
                override fun onReplyClick(index: Int) {}
            }
        )
    }

    private var fetcher: ContributionsFetcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(
            doWork = {

                fetcher = getFetcher()

                contributions.clear()
                contributions.addAll(fetcher?.fetchNext() ?: listOf())
            },
            onPost = {
                controller.setContributions(contributions)
            }
        )
    }

    fun reload(sorting: ContributionsSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            doAsync(
                doWork = {

                    fetcher!!.setSorting(sorting)

                    contributions.clear()
                    contributions.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setContributions(contributions)
                }
            )
        }

        if (timePeriod != null) {

            doAsync(
                doWork = {

                    fetcher!!.setTimePeriod(timePeriod)

                    contributions.clear()
                    contributions.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setContributions(contributions)
                }
            )
        }
    }

    private fun getFetcher(): ContributionsFetcher? {

        return when (passedTag) {
            TAG_OVERVIEW -> client?.accountsClient?.overview()
            TAG_SUBMITTED -> client?.accountsClient?.submitted()
            TAG_COMMENTS -> client?.accountsClient?.comments()
            TAG_SAVED -> client?.accountsClient?.saved()
            TAG_UPVOTED -> client?.accountsClient?.upvoted()
            TAG_DOWNVOTED -> client?.accountsClient?.downvoted()
            TAG_GILDED -> client?.accountsClient?.gilded()

            else -> null
        }
    }
}
