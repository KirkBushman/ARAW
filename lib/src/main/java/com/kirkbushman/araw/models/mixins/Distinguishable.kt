package com.kirkbushman.araw.models.mixins

import com.kirkbushman.araw.models.general.Distinguished

interface Distinguishable {

    val rawDistinguished: String?

    val distinguished: Distinguished
}