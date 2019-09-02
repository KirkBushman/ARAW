package com.kirkbushman.araw.models

import com.kirkbushman.araw.RedditClient
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Redditor(

    @Json(name = "id")
    override val id: String,

    @Json(name = "comment_karma")
    override val commentKarma: Int,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "has_verified_email")
    override val hasVerifiedEmail: Boolean,

    @Json(name = "is_employee")
    override val isEmployee: Boolean,

    @Json(name = "is_friend")
    val isFriend: Boolean,

    @Json(name = "is_gold")
    override val isGold: Boolean,

    @Json(name = "is_mod")
    override val isMod: Boolean,

    @Json(name = "link_karma")
    override val linkKarma: Int,

    @Json(name = "name")
    override val name: String

) : Account {

    fun withClient(client: RedditClient): RedditClient.AccountHandler {
        return client.accountHandler(this)
    }

    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is Redditor) {
            return false
        }

        return id == other.id
    }
}
