package com.kirkbushman.araw.utils

import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Friend
import com.kirkbushman.araw.models.User
import com.kirkbushman.araw.models.WikiRevision
import com.kirkbushman.araw.models.general.Distinguished
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Distinguishable
import com.kirkbushman.araw.models.mixins.Editable
import com.kirkbushman.araw.models.mixins.Votable
import java.util.*
import kotlin.collections.ArrayList

val Created.createdDate: Date
    get() {
        val milliseconds = created * 1000L
        return Date(milliseconds)
    }

val Created.createdUtcDate: Date
    get() {
        val milliseconds = createdUtc * 1000L
        return Date(milliseconds)
    }

val Distinguishable.distinguished: Distinguished
    get() {
        return when (distinguishedRaw) {
            "moderator" -> Distinguished.MODERATOR
            "admin" -> Distinguished.ADMIN
            "special" -> Distinguished.SPECIAL
            else -> Distinguished.NOT_DISTINGUISHED
        }
    }

val Votable.vote: Vote
    get() {
        return when (likes) {
            true -> Vote.UPVOTE
            false -> Vote.DOWNVOTE
            else -> Vote.NONE
        }
    }

val Editable.hasEdited: Boolean
    get() {

        if (editedRaw is Long) {
            return true
        }

        if (editedRaw is Boolean) {
            return editedRaw as Boolean
        }

        return false
    }

val Editable.edited: Date
    get() {

        if (editedRaw is Long) {
            val milliseconds = (editedRaw as Long) / 1000L
            return Date(milliseconds)
        }

        return Date()
    }

val Friend.addedDate: Date
    get() {
        val milliseconds = added * 1000L
        return Date(milliseconds)
    }

val User.userDate: Date
    get() {
        val milliseconds = date * 1000L
        return Date(milliseconds)
    }

val WikiRevision.timestampDate: Date
    get() {
        val milliseconds = timestamp * 1000L
        return Date(milliseconds)
    }

fun List<CommentData>.toLinearList(): List<CommentData> {

    val list = ArrayList<CommentData>()

    treeIterator()
        .forEach {

            if (it is Comment) {
                val item = it.copy()
                item.replies = null

                list.add(item)
            } else {
                list.add(it)
            }
        }

    return list
}
