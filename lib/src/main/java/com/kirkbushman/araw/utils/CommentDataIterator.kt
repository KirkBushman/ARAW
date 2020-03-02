package com.kirkbushman.araw.utils

import com.kirkbushman.araw.models.mixins.CommentData
import java.util.*

class CommentDataIterator(list: Collection<CommentData>) : Iterator<CommentData> {

    private val stack = ArrayDeque<CommentData>(list)

    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    override fun next(): CommentData {
        val item = stack.pop()

        if (item.hasReplies) {
            item.replies!!
                .asReversed()
                .forEach { stack.push(it) }
        }

        return item
    }
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
operator fun Collection<CommentData>.iterator(): Iterator<CommentData> {
    return CommentDataIterator(this)
}
