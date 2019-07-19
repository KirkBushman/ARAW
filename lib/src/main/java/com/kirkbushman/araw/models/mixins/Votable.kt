package com.kirkbushman.araw.models.mixins

interface Votable {

    val id: String

    val fullname: String

    val likes: Boolean?

    val score: Int
}
