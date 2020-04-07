package com.kirkbushman.araw.models.mixins

/**
 * Base interface for the elements in the comments section of a submission.
 *
 * @property depth An integer representing the nested level of this comment.
 *
 * @property parentFullname Fullname of the thing this comment is a reply to,
 * either the link or a comment in it.
 *
 * @property hasReplies If the node has children nodes.
 *
 * @property replies The list of children nodes.
 *
 * @property repliesSize The number of children nodes.
 *
 */
interface CommentData : Contribution {

    val depth: Int

    val parentFullname: String

    val hasReplies: Boolean

    val replies: List<CommentData>?

    val repliesSize: Int
}
