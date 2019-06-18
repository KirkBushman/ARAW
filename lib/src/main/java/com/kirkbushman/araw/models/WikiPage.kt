package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@JsonClass(generateAdapter = true)
@Parcelize
data class WikiPage(

    @Json(name = "content_md")
    val contentMkdn: String,

    @Json(name = "content_html")
    val contentHtml: String,

    @Json(name = "revision_date")
    val revisionRaw: Long?

) : Parcelable, Serializable {

    val revisionDate: Date
        get() {

            if (revisionRaw != null) {
                val milliseconds = revisionRaw / 1000L
                return Date(milliseconds)
            }

            return Date()
        }
}
