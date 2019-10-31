package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class User(

    @Json(name = "id")
    val id: String,

    @Json(name = "rel_id")
    val relId: String,

    @Json(name = "author_flair_text")
    val authorFlairText: String?,

    @Json(name = "author_flair_css_class")
    val authorFlairCssClass: String?,

    @Json(name = "name")
    val name: String,

    @Json(name = "mod_permissions")
    val modPermissions: List<String>,

    @Json(name = "date")
    val date: Long

) : Parcelable
