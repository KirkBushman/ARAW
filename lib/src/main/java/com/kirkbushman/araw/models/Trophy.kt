package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Trophy(

    @Json(name = "id")
    val id: String?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "icon_70")
    val icon70: String,

    @Json(name = "icon_40")
    val icon40: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "url")
    val url: String?

) : Parcelable
