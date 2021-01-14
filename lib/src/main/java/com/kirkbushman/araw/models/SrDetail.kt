package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.base.Thing
import com.kirkbushman.araw.models.commons.ImageDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SrDetail(

    //sr_detail doesn't have an ID field
    @Json(name = "name")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "default_set")
    val defaultSet: Boolean,

    @Json(name = "banner_img")
    val bannerImg: String,

    @Json(name = "community_icon")
    val communityIcon: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "user_is_muted")
    val userIsMuted: Boolean,

    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "header_img")
    val headerImg: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "user_is_moderator")
    val userIsModerator: Boolean,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "icon_size")
    val iconSize: List<Long>,

    @Json(name = "primary_color")
    val primaryColor: String,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "icon_color")
    val iconColor: String,

    @Json(name = "subscribers")
    val subscribers: Long,

    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,

    @Json(name = "key_color")
    val keyColor: String,

    @Json(name = "created_utc")
    val createdUTC: Long,

    @Json(name = "banner_size")
    val bannerSize: List<Long>,

    @Json(name = "public_description")
    val publicDescription: String,

    @Json(name = "user_is_subscriber")
    val userIsSubscriber: Boolean

) : Thing, Parcelable
