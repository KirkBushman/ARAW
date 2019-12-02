package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Thing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Subreddit(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    val fullname: String,

    @Json(name = "accounts_active")
    val accountsActive: Int?,

    @Json(name = "accounts_active_is_fuzzed")
    val accountsActiveIsFuzzed: Boolean?,

    @Json(name = "allow_images")
    val allowImages: Boolean,

    @Json(name = "spoilers_enabled")
    val areSpoilersEnabled: Boolean,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "description")
    val description: String,

    @Json(name = "description_html")
    val descriptionHtml: String?,

    @Json(name = "display_name")
    val displayName: String,

    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,

    @Json(name = "header_img")
    val headerImg: String?,

    @Json(name = "header_size")
    val headerImgSize: List<Int>?,

    @Json(name = "header_title")
    val headerTitle: String?,

    @Json(name = "icon_img")
    val iconImg: String?,

    @Json(name = "user_is_banned")
    val isBanned: Boolean?,

    @Json(name = "user_is_contributor")
    val isContributor: Boolean?,

    @Json(name = "user_is_moderator")
    val isModerator: Boolean?,

    @Json(name = "user_is_muted")
    val isMuted: Boolean?,

    @Json(name = "quarantine")
    val isQuarantined: Boolean?,

    @Json(name = "user_is_subscriber")
    val isSubscriber: Boolean,

    @Json(name = "wiki_enabled")
    val isWikiEnabled: Boolean?,

    @Json(name = "lang")
    val lang: String,

    @Json(name = "over18")
    val over18: Boolean,

    @Json(name = "public_description")
    val publicDescription: String,

    @Json(name = "public_description_html")
    val publicDescriptionHtml: String?,

    @Json(name = "subscribers")
    val subscribers: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String

) : Thing, Created, Parcelable
