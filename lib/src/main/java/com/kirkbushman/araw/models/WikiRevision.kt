package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class WikiRevision(

    @Json(name = "id")
    val id: String,

    @Json(name = "revision_hidden")
    val revisionHidden: Boolean,

    @Json(name = "page")
    val page: String,

    val timestamp: Long

) : Parcelable
