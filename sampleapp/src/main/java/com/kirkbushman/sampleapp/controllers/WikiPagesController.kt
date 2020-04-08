package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.sampleapp.models.wikiPage

class WikiPagesController(callback: WikiPageCallback) : BaseController<String, WikiPagesController.WikiPageCallback>(callback) {

    interface WikiPageCallback : BaseCallback {

        fun onPageClick(items: List<String>, index: Int)
    }

    override fun itemModel(index: Int, it: String, callback: WikiPageCallback?) {

        wikiPage {
            id(it)
            name(it)
            listener { _ -> callback?.onPageClick(items, index) }
        }
    }
}
