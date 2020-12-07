package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.fragments.base.BaseControllerFragment
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmissionFragment : BaseControllerFragment<Submission, SubmissionController.SubmissionCallback>() {

    companion object {

        private const val PASSED_TAG = "passed_tag_args"

        const val TAG_FRONTPAGE = "frontpage"
        const val TAG_ALL = "all"
        const val TAG_POPULAR = "popular"
        const val TAG_FRIENDS = "friends"

        fun newInstance(type: String): SubmissionFragment {

            val fr = SubmissionFragment()
            val args = Bundle()

            args.putString(PASSED_TAG, type)
            fr.arguments = args

            return fr
        }
    }

    private val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    override val callback = object : SubmissionController.SubmissionCallback {

        override fun onUpvoteClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.vote(Vote.UPVOTE, submission)
                }
            )
        }

        override fun onNoneClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.vote(Vote.NONE, submission)
                }
            )
        }

        override fun onDownClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.vote(Vote.DOWNVOTE, submission)
                }
            )
        }

        override fun onSaveClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.save(!submission.isSaved, submission)
                }
            )
        }

        override fun onHideClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.hide(submission)
                }
            )
        }

        override fun onLockClick(index: Int) {

            DoAsync(
                doWork = {
                    val submission = items[index]
                    client.contributionsClient.lock(submission)
                }
            )
        }

        override fun onReplyClick(index: Int) = Unit
    }

    override val controller by lazy {

        SubmissionController(callback)
    }

    private var fetcher: SubmissionsFetcher? = null

    override fun fetchItem(client: RedditClient?): Collection<Submission>? {

        fetcher = when (passedTag) {
            TAG_FRONTPAGE -> client?.subredditsClient?.frontpage()
            TAG_ALL -> client?.subredditsClient?.all()
            TAG_POPULAR -> client?.subredditsClient?.popular()
            TAG_FRIENDS -> client?.subredditsClient?.friends()

            else -> null
        }

        return fetcher?.fetchNext()
    }

    fun reload(sorting: SubmissionsSorting? = null, timePeriod: TimePeriod? = null) {

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
