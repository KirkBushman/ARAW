package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.SubmissionSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_submissions.*

class SubmissionFragment : Fragment(R.layout.fragment_submission) {

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

    val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    private val client by lazy { TestApplication.instance.getClient() }
    private val fetcher by lazy {

        when (passedTag) {
            TAG_FRONTPAGE -> client?.commonSubreddits?.frontpage()
            TAG_ALL -> client?.commonSubreddits?.all()
            TAG_POPULAR -> client?.commonSubreddits?.popular()
            TAG_FRIENDS -> client?.commonSubreddits?.friends()

            else -> null
        }
    }

    private val submissions = ArrayList<Submission>()
    private val controller by lazy {

        SubmissionController(object : SubmissionController.SubmissionCallback {

            override fun onUpvoteClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributions?.vote(Vote.UPVOTE, submission)
                })
            }

            override fun onNoneClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributions?.vote(Vote.NONE, submission)
                })
            }

            override fun onDownClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributions?.vote(Vote.DOWNVOTE, submission)
                })
            }

            override fun onSaveClick(index: Int) {

                doAsync(doWork = {
                    val submission = submissions[index]
                    client?.contributions?.save(!submission.saved, submission)
                })
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        list.setController(controller)

        doAsync(doWork = {

            submissions.addAll(fetcher?.fetchNext() ?: listOf())
        }, onPost = {

            controller.setSubmission(submissions)
        })
    }

    fun reload(sorting: SubmissionSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            doAsync(doWork = {

                fetcher!!.setSorting(sorting)

                submissions.clear()
                submissions.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {
                controller.setSubmission(submissions)
            })
        }

        if (timePeriod != null) {

            doAsync(doWork = {

                fetcher!!.setTimePeriod(timePeriod)

                submissions.clear()
                submissions.addAll(fetcher?.fetchNext() ?: listOf())
            }, onPost = {
                controller.setSubmission(submissions)
            })
        }
    }
}