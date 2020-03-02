package com.kirkbushman.araw.models.mixins

interface CommentData : Contribution {

    val depth: Int

    val hasReplies: Boolean

    val replies: List<CommentData>?
}
