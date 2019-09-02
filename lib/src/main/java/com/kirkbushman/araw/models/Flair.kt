package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Flair(

    @Json(name = "id")
    val id: Int,

    @Json(name = "css_class")
    val cssClass: String,

    @Json(name = "text")
    val text: String

) : Parcelable
