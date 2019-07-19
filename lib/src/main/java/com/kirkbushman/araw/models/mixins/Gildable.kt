package com.kirkbushman.araw.models.mixins

import com.kirkbushman.araw.models.general.Gildings

interface Gildable {

    val canGild: Boolean

    val gildings: Gildings
}
