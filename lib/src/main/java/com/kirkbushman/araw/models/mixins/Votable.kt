package com.kirkbushman.araw.models.mixins

/**
 * Base interface for all the items that can be voted on,
 * likes = True -> Upvote, likes = False -> Downvote, likes = null
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of item, e.g. "t1_c3v7f8u"
 *
 * @property likes how the logged-in user has voted on the item,
 * True = upvoted, False = downvoted, null = no vote
 * NOTE: use the extensions to have a practical enum class.
 *
 * @property score the net-score of the item.
 *
 */
interface Votable {

    val id: String

    val fullname: String

    val likes: Boolean?

    val score: Int
}
