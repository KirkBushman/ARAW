package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.submission

class SubmissionController : EpoxyController() {

    private val submissions = ArrayList<Submission>()

    fun setSubmission(submissions: List<Submission>) {
        this.submissions.clear()
        this.submissions.addAll(submissions)
        requestModelBuild()
    }

    override fun buildModels() {

        if (submissions.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        submissions.forEach {

            submission {
                id(it.id)
                subreddit(it.subreddit)
                author(it.author)
                title(it.title)
                body(it.selftext ?: "")
            }
        }
    }
}