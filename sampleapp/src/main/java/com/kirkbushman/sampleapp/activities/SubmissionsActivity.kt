package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.activities.base.BaseSearchControllerActivity
import com.kirkbushman.sampleapp.controllers.SubmissionController
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmissionsActivity : BaseSearchControllerActivity<Submission, SubmissionController.SubmissionCallback>() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, SubmissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

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

    override val controller by lazy { SubmissionController(callback) }

    private var fetcher: SubmissionsFetcher? = null

    override fun fetchItem(client: RedditClient, query: String): Collection<Submission>? {
        fetcher = client.contributionsClient.createSubmissionsFetcher(query, limit = 100L)
        return fetcher?.fetchNext()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
