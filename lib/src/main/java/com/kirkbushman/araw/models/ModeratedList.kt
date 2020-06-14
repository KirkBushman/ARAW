package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.mixins.Created
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ModeratedList(

    @Json(name = "kind")
    val kind: EnvelopeKind?,

    @Json(name = "data")
    val data: List<ModeratedSub>?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class ModeratedSub(

    @Json(name = "name")
    val fullname: String,

    @Json(name = "banner_img")
    val bannerImg: String,

    @Json(name = "banner_size")
    val bannerImgSize: List<Int>?,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "icon_img")
    val iconImg: String,

    @Json(name = "icon_size")
    val iconImgSize: List<Int>?,

    @Json(name = "user_is_subscriber")
    val isSubscriber: Boolean,

    @Json(name = "sr")
    val displayName: String,

    @Json(name = "sr_display_name_prefixed")
    val displayNamePrefixed: String,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "subreddit_type")
    val subredditType: String,

    @Json(name = "subscribers")
    val subscribers: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String

) : Parcelable, Created
