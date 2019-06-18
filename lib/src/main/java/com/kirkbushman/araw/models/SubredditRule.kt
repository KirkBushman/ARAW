package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditRule(

    @Json(name = "kind")
    val kind: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "description_html")
    val descriptionHtml: String,

    @Json(name = "short_name")
    val shortName: String,

    @Json(name = "violation_reason")
    val violationReason: String,

    @Json(name = "priority")
    val priority: Int,

    @Json(name = "created_utc")
    val createdUtc: Long

) : Parcelable, Serializable {

    val createdDate: Date
        get() {
            val milliseconds = createdUtc / 1000L
            return Date(milliseconds)
        }

    override fun toString(): String {
        return "SubredditRule { " +
                "kind: $kind, " +
                "description: $description, " +
                "descriptionHtml: $descriptionHtml, " +
                "shortName: $shortName, " +
                "violationReason: $violationReason, " +
                "priority: $priority, " +
                "createdUtc: $createdUtc " +
                "}"
    }
}