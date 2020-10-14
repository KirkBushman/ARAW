package com.kirkbushman.araw.utils

import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Friend
import com.kirkbushman.araw.models.User
import com.kirkbushman.araw.models.WikiRevision
import com.kirkbushman.araw.models.enums.Distinguished
import com.kirkbushman.araw.models.enums.Vote
import com.kirkbushman.araw.models.base.CommentData
import com.kirkbushman.araw.models.base.Created
import com.kirkbushman.araw.models.base.Distinguishable
import com.kirkbushman.araw.models.base.Editable
import com.kirkbushman.araw.models.base.Votable
import java.util.*
import kotlin.collections.ArrayList

/**
 * Useful extension to convert the creation date property,
 * from the Long unixtime, to a more practical Date object.
 * @return the Date format of the item creation date.
 */
val Created.createdDate: Date
    get() {
        val milliseconds = created * 1000L
        return Date(milliseconds)
    }

/**
 * Useful extension to convert the utc creation date property,
 * from the Long unixtime, to a more practical Date object.
 * @return the Date format of the item utc creation date.
 */
val Created.createdUtcDate: Date
    get() {
        val milliseconds = createdUtc * 1000L
        return Date(milliseconds)
    }

/**
 * Useful extension to map to an enum.
 * @return the Distinguished status of the item.
 */
val Distinguishable.distinguished: Distinguished
    get() {
        return when (distinguishedRaw) {
            "moderator" -> Distinguished.MODERATOR
            "admin" -> Distinguished.ADMIN
            "special" -> Distinguished.SPECIAL
            else -> Distinguished.NOT_DISTINGUISHED
        }
    }

/**
 * Useful extension to map to an enum.
 * @return the Vote status of the item.
 */
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
