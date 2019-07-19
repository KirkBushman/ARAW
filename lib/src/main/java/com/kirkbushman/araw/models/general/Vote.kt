package com.kirkbushman.araw.models.general

enum class Vote(val dir: Int) {

    UPVOTE(1),
    DOWNVOTE(-1),
    NONE(0)
}
