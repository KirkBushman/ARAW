package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Flair(

    @Json(name = "id")
    val id: String,

    @Json(name = "allowable_content")
    val allowableContent: String,

    @Json(name = "background_color")
    val backgroundColor: String,

    @Json(name = "css_class")
    val cssClass: String,

    @Json(name = "text")
    val text: String,

    @Json(name = "text_color")
    val textColor: String,

    @Json(name = "text_editable")
    val textEditable: Boolean,

    @Json(name = "type")
    val type: String

) : Parcelable
