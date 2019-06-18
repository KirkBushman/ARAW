package com.kirkbushman.araw.utils

import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.models.mixins.Created
import java.util.*
import kotlin.collections.ArrayList

fun Created.createdDate(): Date {
    val milliseconds = created / 1000L
    return Date(milliseconds)
}

fun Created.createdUtcDate(): Date {
    val milliseconds = createdUtc / 1000L
    return Date(milliseconds)
}

fun List<CommentData>.toCommentSequence(): ArrayList<CommentData> {

    val sequence = ArrayList<CommentData>()
    val stack = ArrayDeque<CommentData>()

    stack.addAll(this)

    while (!stack.isEmpty()) {
        val item = stack.pop()
        sequence.add(item)

        if (item is Comment) {

            if (item.hasReplies) {
                item.replies.asReversed().forEach {

                    stack.push(it)
                }
            }
        }
    }

    return sequence
}