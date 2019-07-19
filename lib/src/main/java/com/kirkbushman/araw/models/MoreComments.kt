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

    @Json(name = "parent_id")
    val parentId: String,

    @Json(name = "children")
    val children: List<String>

) : CommentData, Parcelable {

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is MoreComments) {
            return false
        }

        return id == other.id
    }

    override fun toString(): String {
        return "MoreComments { " +
                "id: $id, " +
                "fullname: $fullname, " +
                "count: $count, " +
                "depth: $depth, " +
                "parentId: $parentId, " +
                "children: $children " +
                "}"
    }
}
