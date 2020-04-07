package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.CommentData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * This class is used to load more comments in the tree,
 * when a comment or a group of comment is too deep.
 *
 * Instances of this class along with the class MoreComments is organized
 * in a tree structure, that composes a Submission comments section.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of comment, e.g. "t1_c3v7f8u"
 *
 * @property count number of comments to load.
 *
 * @property depth An integer representing the nested level of this node.
 *
 * @property parentId ID of the thing this comment is a reply to,
 * either the link or a comment in it.
 *
 * @property parentFullname fullname of the thing this comment is a reply to,
 * either the link or a comment in it.
 *
 * @property children A list of String ids that are the additional things that
 * can be downloaded but are not because there are too many to list.
 *
 * @property replies always null
 *
 * @property hasReplies always false
 *
 * @property repliesSize always 0
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class MoreComments(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "count")
    val count: Int,

    @Json(name = "depth")
    override val depth: Int,

    @Json(name = "parent_id")
    val parentId: String,

    @Json(name = "children")
    val children: List<String>,

    @Transient
    override val parentFullname: String = parentId,
    @Transient
    override val replies: List<CommentData>? = null,
    @Transient
    override val hasReplies: Boolean = false,
    @Transient
    override val repliesSize: Int = 0

) : CommentData, Parcelable
