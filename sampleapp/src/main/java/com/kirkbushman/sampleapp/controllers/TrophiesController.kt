package com.kirkbushman.sampleapp.controllers

import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.sampleapp.models.trophy

class TrophiesController : BaseController2<Trophy>() {

    override fun itemModel(index: Int, it: Trophy, callback: BaseCallback?) {

        trophy {
            id(it.id)
            name(it.fullname)
        }
    }
}
