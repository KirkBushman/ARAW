package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.araw.models.base.Contribution
import com.kirkbushman.araw.models.base.Votable
import com.kirkbushman.sampleapp.activities.RedditorInfoActivity
import com.kirkbushman.sampleapp.controllers.ContributionController
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.fragments.base.BaseControllerFragment
import com.kirkbushman.sampleapp.util.DoAsync

class ContributionFragment : BaseControllerFragment<Contribution, SubmissionController.SubmissionCallback>() {

    companion object {

        private const val PASSED_TAG = "passed_tag_args"

        const val TAG_OVERVIEW = "overview"
        const val TAG_SUBMITTED = "submitted"
        const val TAG_COMMENTS = "comments"
        const val TAG_GILDED = "gilded"

        fun newInstance(type: String): ContributionFragment {

            val fr = ContributionFragment()
            val args = Bundle()

            args.putString(PASSED_TAG, type)
            fr.arguments = args

            return fr
        }
    }

    val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    private val username: String
        get() {
            return if (activity is RedditorInfoActivity)
                (activity as RedditorInfoActivity).getUsername()
            else
                ""
        }

    override val callback = object : SubmissionController.SubmissionCallback {

        override fun onUpvoteClick(index: Int) {

            DoAsync(
                doWork = {
                    val votable = items[index] as Votable
                    client?.contributionsClient?.vote(Vote.UPVOTE, votable)
                }
            )
        }

        override fun onNoneClick(index: Int) {

            DoAsync(
                doWork = {
                    val votable = items[index] as Votable
                    client?.contributionsClient?.vote(Vote.NONE, votable)
                }
            )
        }

        override fun onDownClick(index: Int) {

            DoAsync(
                doWork = {
                    val votable = items[index] as Votable
                    client?.contributionsClient?.vote(Vote.DOWNVOTE, votable)
                }
            )
        }

        override fun onSaveClick(index: Int) {

            DoAsync(
                doWork = {
                    when (val contribution = items[index]) {
                        is Submission -> client?.contributionsClient?.save(!contribution.isSaved, contribution)
                        is Comment -> client?.contributionsClient?.save(!contribution.isSaved, contribution)

                        else -> {}
                    }
                }
            )
        }

        override fun onHideClick(index: Int) = Unit
        override fun onLockClick(index: Int) = Unit
        override fun onReplyClick(index: Int) = Unit
    }

    override val controller by lazy {

        ContributionController(callback)
    }

    private var fetcher: ContributionsFetcher? = null

    override fun fetchItem(client: RedditClient?): Collection<Contribution>? {

        fetcher = when (passedTag) {
            TAG_OVERVIEW -> client?.redditorsClient?.overview(username)
            TAG_SUBMITTED -> client?.redditorsClient?.submitted(username)
            TAG_COMMENTS -> client?.redditorsClient?.comments(username)
            TAG_GILDED -> client?.redditorsClient?.gilded(username)

            else -> null
        }

        return fetcher?.fetchNext()
    }

    fun refresh() {

        DoAsync(
            doWork = {

                items.clear()
                items.addAll(fetcher?.fetchNext() ?: listOf())
            },
            onPost = {

                controller.setItems(items)
            }
        )
    }

    fun reload(sorting: ContributionsSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            DoAsync(
                doWork = {

                    fetcher!!.setSorting(sorting)

                    items.clear()
                    items.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {

                    controller.setItems(items)
                }
            )
        }

        if (timePeriod != null) {

            DoAsync(
                doWork = {

                    fetcher!!.setTimePeriod(timePeriod)

                    items.clear()
                    items.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {

                    controller.setItems(items)
                }
            )
        }
    }
}
