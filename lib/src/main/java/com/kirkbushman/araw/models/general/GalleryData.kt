package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class GalleryData(

    @Json(name = "items")
    val items: List<GalleryMediaItem>

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class GalleryMediaItem(

    @Json(name = "id")
    val id: Long,

    @Json(name = "media_id")
    val mediaId: String

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class GalleryMedia(

    @Json(name = "id")
    val id: String,

    @Json(name = "status")
    val status: String,

    @Json(name = "e")
    val e: String,

    @Json(name = "m")
    val m: String?,

    @Json(name = "p")
    val p: List<GalleryImageData>?,

    @Json(name = "s")
    val s: GalleryImageData?,

    @Json(name = "dashUrl")
    val dashUrl: String?,

    @Json(name = "hlsUrl")
    val hlsUrl: String?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class GalleryImageData(

    @Json(name = "u")
    val u: String?,

    @Json(name = "gif")
    val gif: String?,

    @Json(name = "mp4")
    val mp4: String?,

    @Json(name = "x")
    val x: Int,

    @Json(name = "y")
    val y: Int

) : Parcelable
