package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Friend(

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "rel_id")
    val relId: String,

    @Json(name = "date")
    val added: Long

) : Parcelable
