package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.SuspendedRedditor
import com.kirkbushman.araw.models.base.RedditorData
import com.kirkbushman.araw.utils.createdDate
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController2
import com.kirkbushman.sampleapp.models.redditor
import com.kirkbushman.sampleapp.models.suspendedRedditor

class RedditorController : BaseController2<RedditorData>() {

    override fun itemModel(index: Int, it: RedditorData, callback: BaseCallback?) {

        if (it is Redditor) {

            redditor {
                id(it.id)
                redditorNameText(it.fullname)
                redditorCreatedText(it.createdDate.toString())
                redditorKarmaText((it.commentKarma + it.linkKarma).toString())
            }
        }

        if (it is SuspendedRedditor) {

            suspendedRedditor {
                id(it.fullname)
                redditorNameText(it.fullname)
            }
        }
    }
}
