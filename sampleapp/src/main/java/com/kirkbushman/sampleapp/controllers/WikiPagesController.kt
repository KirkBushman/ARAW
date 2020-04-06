package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.wikiPage

class WikiPagesController(private val callback: WikiPageCallback) : EpoxyController() {

    interface WikiPageCallback {

        fun onPageClick(index: Int)
    }

    private val wikiPages = ArrayList<String>()

    fun setWikiPages(wikiPages: Collection<String>) {
        this.wikiPages.clear()
        this.wikiPages.addAll(wikiPages)
        requestModelBuild()
    }

    override fun buildModels() {

        if (wikiPages.isEmpty()) {

            empty {
                id("empty_model")
            }
        }

        wikiPages.forEachIndexed { index, it ->

            wikiPage {
                id(it)
                name(it)
                listener { _ -> callback.onPageClick(index) }
            }
        }
    }
}
