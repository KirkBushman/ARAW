package com.kirkbushman.araw.models.mixins

import com.kirkbushman.araw.models.general.Vote

interface Votable {

    val likes: Boolean?

    val vote: Vote

    val score: Int
}