package com.kirkbushman.araw.models.commons

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

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
