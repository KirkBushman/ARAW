package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RedditorSubreddit(

    @Json(name = "name")
    val fullname: String,

    @Json(name = "banner_img")
    val bannerImg: String?,

    @Json(name = "banner_size")
    val bannerImgSize: List<Int>?,

    @Json(name = "community_icon")
    val communityIcon: String?,

    @Json(name = "description")
    val description: String,

    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,

    @Json(name = "is_default_banner")
    val hasDefaultBanner: Boolean,

    @Json(name = "is_default_icon")
    val hasDefaultIcon: Boolean,

    @Json(name = "header_img")
    val headerImg: String?,

    @Json(name = "header_size")
    val headerImgSize: List<Int>?,

    @Json(name = "icon_img")
    val iconImg: String?,

    @Json(name = "icon_size")
    val iconImgSize: List<Int>?,

    @Json(name = "user_is_banned")
    val isBanned: Boolean?,

    @Json(name = "user_is_contributor")
    val isContributor: Boolean?,

    @Json(name = "user_is_moderator")
    val isModerator: Boolean?,

    @Json(name = "user_is_muted")
    val isMuted: Boolean?,

    @Json(name = "user_is_subscriber")
    val isSubscriber: Boolean?,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "public_description")
    val publicDescription: String,

    @Json(name = "subreddit_type")
    val subredditType: String,

    @Json(name = "subscribers")
    val subscribers: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String

) : Parcelable
