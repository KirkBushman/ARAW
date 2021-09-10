package com.kirkbushman.araw.models.commons

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class FlairRichtext(

    @Json(name = "a")
    val textRepresentation: String?,

    @Json(name = "e")
    val type: String?,

    @Json(name = "t")
    val text: String?,

    @Json(name = "u")
    val url: String?

) : Parcelable