package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.utils.createdDate
import com.kirkbushman.sampleapp.models.redditor

class RedditorController : BaseController2<Redditor>() {

    override fun itemModel(index: Int, it: Redditor, callback: BaseCallback?) {

        redditor {
            id(it.id)
            redditorName(it.fullname)
            redditorCreated(it.createdDate.toString())
            redditorKarma((it.commentKarma + it.linkKarma).toString())
        }
    }
}
