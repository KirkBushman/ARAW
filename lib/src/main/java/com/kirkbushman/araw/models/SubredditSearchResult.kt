package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditSearchResult(

    @Json(name = "subreddits")
    val subreddits: List<SubredditSearchItem>

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditSearchItem(

    @Json(name = "active_user_count")
    val activeUserCount: Int?,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "key_color")
    val keyColor: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "subscriber_count")
    val subscriberCount: Int,

    @Json(name = "allow_chat_post_creation")
    val allowChatPostCreation: Boolean?,

    @Json(name = "allow_images")
    val allowImages: Boolean

) : Parcelable
