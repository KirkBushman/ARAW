package com.kirkbushman.araw.models.mixins

/**
 * Base interface for all items that be replied to,
 * including Submission and Comment.
 *
 * @property fullname Fullname of the item, e.g. "t1_c3v7f8u"
 *
 */
interface Replyable {

    val fullname: String
}
