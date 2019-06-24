package com.kirkbushman.araw.models.mixins

interface Votable {

    val id: String

    val name: String

    val likes: Boolean?

    val score: Int
}