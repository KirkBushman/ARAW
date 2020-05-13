package com.kirkbushman.araw.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * This class represents a Redditor, that is not the current logged in one.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname fullname of this class.
 *
 * @property created The unix-time Long representing the creation date of the comment.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the comment.
 *
 * @property commentKarma karma obtained through submitting a comment.
 *
 * @property linkKarma karma obtained through submitting a post (Submission).
 *
 * @property hasVerifiedEmail if the user has a verified email.
 *
 * @property isFriend if the user has been marked as friend by the current logged in user.
 *
 * @property isEmployee if the user is an employee of Reddit.
 *
 * @property isGold if the user has a gold status.
 *
 * @property isMod if the user is Mod.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Redditor(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

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

    @Json(name = "hide_from_robots")
    override val isHidingFromRobots: Boolean,

    @Json(name = "is_mod")
    override val isMod: Boolean,

    @Json(name = "link_karma")
    override val linkKarma: Int

) : Account
