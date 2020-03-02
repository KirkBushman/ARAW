package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.CommentData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

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

    @Transient
    override val replies: List<CommentData>? = null,
    @Transient
    override val hasReplies: Boolean = false,

    @Json(name = "parent_id")
    val parentId: String,

    @Json(name = "children")
    val children: List<String>

) : CommentData, Parcelable
