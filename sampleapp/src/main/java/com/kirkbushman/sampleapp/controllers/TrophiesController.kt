package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.trophy

class TrophiesController : EpoxyController() {

    private val trophies = ArrayList<Trophy>()

    fun setTrophies(trophies: List<Trophy>) {
        this.trophies.clear()
        this.trophies.addAll(trophies)
        requestModelBuild()
    }

    override fun buildModels() {

        if (trophies.isEmpty()) {
            empty {
                id("empty_model")
            }
        }

        trophies.forEach {

            trophy {
                id(it.id)
                name(it.name)
            }
        }
    }
}