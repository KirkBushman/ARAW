package com.kirkbushman.araw.models.mixins

import java.util.*

interface Editable {

    val hasEdited: Boolean

    val edited: Date
    val editedRaw: Any
}