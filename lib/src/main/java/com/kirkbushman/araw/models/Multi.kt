package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.base.Created
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Multi(

    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "description_md")
    val descriptionMkdn: String,

    @Json(name = "description_html")
    val descriptionHtml: String,

    @Json(name = "can_edit")
    val canEdit: Boolean,

    @Json(name = "copied_from")
    val copiedFrom: String?,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "icon_url")
    val iconUrl: String,

    @Json(name = "is_subscriber")
    val isSubscriber: Boolean,

    @Json(name = "is_favorited")
    val isFavorited: Boolean,

    @Json(name = "name")
    val name: String,

    @Json(name = "num_subscribers")
    val numSubscribers: Int,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "owner_id")
    val ownerId: String,

    @Json(name = "owner")
    val ownerName: String,

    @Json(name = "path")
    val path: String,

    @Json(name = "subreddits")
    val subreddits: List<MultiSub>,

    @Json(name = "visibility")
    val visibility: String,

) : Created, Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MultiSub(

    @Json(name = "name")
    val name: String

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MultiDescription(

    @Json(name = "body_md")
    val bodyMkdn: String,

    @Json(name = "body_html")
    val bodyHtml: String

) : Parcelable
