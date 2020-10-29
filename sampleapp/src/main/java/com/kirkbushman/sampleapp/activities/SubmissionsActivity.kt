package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.activities.base.BaseActivity
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.util.doAsync
import kotlinx.android.synthetic.main.activity_submissions.*

class SubmissionsActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {

            val intent = Intent(context, SubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }

    private val submissions = ArrayList<Submission>()
    private val controller by lazy {

        SubmissionController(

            object : SubmissionController.SubmissionCallback {

                override fun onUpvoteClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.UPVOTE, submission)
                        }
                    )
                }

                override fun onNoneClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.NONE, submission)
                        }
                    )
                }

                override fun onDownClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.vote(Vote.DOWNVOTE, submission)
                        }
                    )
                }

                override fun onSaveClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.save(!submission.isSaved, submission)
                        }
                    )
                }

                override fun onHideClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.hide(submission)
                        }
                    )
                }

                override fun onLockClick(index: Int) {

                    doAsync(
                        doWork = {
                            val submission = submissions[index]
                            client?.contributionsClient?.lock(submission)
                        }
                    )
                }

                override fun onReplyClick(index: Int) {}
            }
        )
    }

    private var fetcher: SubmissionsFetcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submissions)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        search_bttn.setOnClickListener {

            val subredditName = search.text.toString().trim()

            doAsync(
                doWork = {

                    fetcher = client?.contributionsClient?.submissions(subredditName, limit = 100L)

                    submissions.clear()
                    submissions.addAll(fetcher?.fetchNext() ?: listOf())
                },
                onPost = {
                    controller.setSubmission(submissions)
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sorting_time, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.item_sorting_hot -> { reloadSubmission(sorting = SubmissionsSorting.HOT); true }
            R.id.item_sorting_best -> { reloadSubmission(sorting = SubmissionsSorting.BEST); true }
            R.id.item_sorting_new -> { reloadSubmission(sorting = SubmissionsSorting.NEW); true }
            R.id.item_sorting_rising -> { reloadSubmission(sorting = SubmissionsSorting.RISING); true }
            R.id.item_sorting_controversial -> { reloadSubmission(sorting = SubmissionsSorting.CONTROVERSIAL); true }
            R.id.item_sorting_top -> { reloadSubmission(sorting = SubmissionsSorting.TOP); true }

            R.id.item_timeperiod_hour -> { reloadSubmission(timePeriod = TimePeriod.LAST_HOUR); true }
            R.id.item_timeperiod_day -> { reloadSubmission(timePeriod = TimePeriod.LAST_DAY); true }
            R.id.item_timeperiod_week -> { reloadSubmission(timePeriod = TimePeriod.LAST_WEEK); true }
            R.id.item_timeperiod_month -> { reloadSubmission(timePeriod = TimePeriod.LAST_MONTH); true }
            R.id.item_timeperiod_year -> { reloadSubmission(timePeriod = TimePeriod.LAST_YEAR); true }
            R.id.item_timeperiod_all -> { reloadSubmission(timePeriod = TimePeriod.ALL_TIME); true }

            else -> { false }
        }
    }

    private fun reloadSubmission(sorting: SubmissionsSorting? = null, timePeriod: TimePeriod? = null) {

        if (sorting != null) {

            doAsync(
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

            doAsync(
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
