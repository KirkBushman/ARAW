package com.kirkbushman.araw.models.mixins

interface Contribution : Thing {

    override val id: String

    val name: String
}