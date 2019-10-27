package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Karma(

    @Json(name = "sr")
    val subreddit: String,

    @Json(name = "comment_karma")
    val commentKarma: Int,

    @Json(name = "link_karma")
    val linkKarma: Int

) : Parcelable
