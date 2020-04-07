package com.kirkbushman.araw.models.mixins

/**
 * Base interface for all the data submitted by a user.
 * e.g. Submission, Comment, Message
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of comment, e.g. "t1_c3v7f8u"
 *
 */
interface Contribution : Thing {

    override val id: String

    override val fullname: String
}
