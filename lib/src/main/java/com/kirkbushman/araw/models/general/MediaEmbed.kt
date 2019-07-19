package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class MediaEmbed(

    @Json(name = "content")
    val content: String?,

    @Json(name = "width")
    val width: Int?,

    @Json(name = "height")
    val height: Int?

) : Parcelable
