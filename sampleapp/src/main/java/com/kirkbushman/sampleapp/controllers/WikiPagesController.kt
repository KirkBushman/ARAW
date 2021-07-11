package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.models.wikiPage

class WikiPagesController(

    callback: WikiPageCallback

) : BaseController<String, WikiPagesController.WikiPageCallback>(callback) {

    interface WikiPageCallback : BaseCallback {

        fun onPageClick(items: List<String>, index: Int)
    }

    override fun itemModel(index: Int, it: String, callback: WikiPageCallback?) {

        wikiPage {
            id(it)
            nameText(it)
            listener { _ -> callback?.onPageClick(this@WikiPagesController.items, index) }
        }
    }
}
