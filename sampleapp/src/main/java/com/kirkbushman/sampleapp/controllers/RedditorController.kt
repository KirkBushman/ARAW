package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.utils.createdDate
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController2
import com.kirkbushman.sampleapp.models.redditor

class RedditorController : BaseController2<Redditor>() {

    override fun itemModel(index: Int, it: Redditor, callback: BaseCallback?) {

        redditor {
            id(it.id)
            redditorNameText(it.fullname)
            redditorCreatedText(it.createdDate.toString())
            redditorKarmaText((it.commentKarma + it.linkKarma).toString())
        }
    }
}
