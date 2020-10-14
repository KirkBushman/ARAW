package com.kirkbushman.araw.models.commons

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Media(

    @Json(name = "type")
    val type: String?,

    @Json(name = "oembed")
    val oEmbed: OEmbed?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class OEmbed(

    @Json(name = "title")
    val title: String?,

    @Json(name = "type")
    val type: String?,

    @Json(name = "html")
    val html: String?,

    @Json(name = "provider_name")
    val providerName: String?,

    @Json(name = "provider_url")
    val providerUrl: String?,

    @Json(name = "author_name")
    val authorName: String?,

    @Json(name = "author_url")
    val authorUrl: String?,

    @Json(name = "thumbnail_url")
    val thumbnailUrl: String?,

    @Json(name = "thumbnail_width")
    val thumbnailWidth: Int?,

    @Json(name = "thumbnail_height")
    val thumbnailHeight: Int?,

    @Json(name = "width")
    val width: Int?,

    @Json(name = "height")
    val height: Int?

) : Parcelable
