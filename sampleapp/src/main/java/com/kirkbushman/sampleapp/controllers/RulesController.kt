package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.rule

class RulesController : EpoxyController() {

    private val rules = ArrayList<SubredditRule>()

    fun setRules(rules: List<SubredditRule>) {
        this.rules.clear()
        this.rules.addAll(rules)
        requestModelBuild()
    }

    override fun buildModels() {

        if (rules.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        rules.forEach {

            rule {
                id(it.priority)
                priority(it.priority)
                shortName(it.shortName)
            }
        }
    }
}