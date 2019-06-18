package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.sampleapp.models.comment
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.submission

class ContributionController : EpoxyController() {

    private val contributions = ArrayList<Contribution>()

    fun setContributions(contributions: List<Contribution>) {
        this.contributions.clear()
        this.contributions.addAll(contributions)
        requestModelBuild()
    }

    override fun buildModels() {

        if (contributions.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        contributions.forEach {

            if (it is Comment) {
                comment {
                    id(it.id)
                    author(it.author)
                    body(it.body)
                }
            }

            if (it is Submission) {
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
}