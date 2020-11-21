package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.util.DoAsync
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
            TAG_FRONTPAGE -> client?.subredditsClient?.frontpage()
            TAG_ALL -> client?.subredditsClient?.all()
            TAG_POPULAR -> client?.subredditsClient?.popular()
            TAG_FRIENDS -> client?.subredditsClient?.friends()

            else -> null
        }
    }

    private val submissions = ArrayList<Submission>()
    private val controller by lazy {

        SubmissionController(

            object : SubmissionController.SubmissionCallback {

                override fun onUpvoteClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.UPVOTE, submission)
                        }
                    )
                }

                override fun onNoneClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.NONE, submission)
                        }
                    )
                }

                override fun onDownClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.DOWNVOTE, submission)
                        }
                    )
                }

                override fun onSaveClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.save(!submission.isSaved, submission)
                        }
                    )
                }

                override fun onHideClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.hide(submission)
                        }
                    )
                }

                override fun onLockClick(index: Int) {

                    DoAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.lock(submission)
                        }
                    )
                }

                override fun onReplyClick(index: Int) = Unit
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        list.setHasFixedSize(true)
        list.setController(controller)

        DoAsync(
            doWork = {

                submissions.clear()
                submissions.addAll(fetcher?.fetchNext() ?: listOf())
            },
            onPost = {

                controller.setSubmission(submissions)
            }
        )
    }

    fun reload(sorting: SubmissionsSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            DoAsync(
                doWork = {

                    fetcher!!.setSorting(sorting)

                    submissions.clear()
                    submissions.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setSubmission(submissions)
                }
            )
        }

        if (timePeriod != null) {

            DoAsync(
                doWork = {

                    fetcher!!.setTimePeriod(timePeriod)

                    submissions.clear()
                    submissions.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setSubmission(submissions)
                }
            )
        }
    }
}
