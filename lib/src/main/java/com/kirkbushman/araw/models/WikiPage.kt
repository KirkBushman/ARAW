package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
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

) : Parcelable {

    val revisionDate: Date
        get() {

            if (revisionRaw != null) {
                val milliseconds = revisionRaw / 1000L
                return Date(milliseconds)
            }

            return Date()
        }
}

/**
 * An enveloped list of strings, that represents the wiki page titles.
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class WikiPageList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: List<String>

) : Parcelable
