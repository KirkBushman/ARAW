package com.kirkbushman.araw.models.mixins

interface CommentData : Contribution {

    val depth: Int

    val parentFullname: String

    val hasReplies: Boolean

    val replies: List<CommentData>?

    val repliesSize: Int
}
