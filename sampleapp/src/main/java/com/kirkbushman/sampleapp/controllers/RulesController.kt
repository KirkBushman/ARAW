package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController2
import com.kirkbushman.sampleapp.models.rule

class RulesController : BaseController2<SubredditRule>() {

    override fun itemModel(index: Int, it: SubredditRule, callback: BaseCallback?) {

        rule {
            id(it.priority)
            priority(it.priority)
            shortName(it.shortName)
        }
    }
}
