package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.utils.createdDate
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.redditor

class RedditorController : EpoxyController() {

    private val redditors = ArrayList<Redditor>()

    fun setRedditors(redditors: Collection<Redditor>) {
        this.redditors.clear()
        this.redditors.addAll(redditors)
        requestModelBuild()
    }

    override fun buildModels() {

        if (redditors.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        redditors.forEach {

            redditor {
                id(it.id)
                redditorName(it.name)
                redditorCreated(it.createdDate.toString())
                redditorKarma((it.commentKarma + it.linkKarma).toString())
            }
        }
    }
}
