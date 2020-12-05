package com.kirkbushman.araw.models.commons

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RedditMedia(

    @Json(name = "reddit_video")
    val redditVideo: RedditVideo?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class RedditVideo(

    @Json(name = "dash_url")
    val dashUrl: String,

    @Json(name = "hls_url")
    val hlsUrl: String,

    @Json(name = "scrubber_media_url")
    val scrubberMediaUrl: String,

    @Json(name = "fallback_url")
    val fallbackUrl: String,

    @Json(name = "width")
    val width: Int,

    @Json(name = "height")
    val height: Int,

    @Json(name = "duration")
    val duration: Int

) : Parcelable
