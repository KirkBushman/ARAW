package com.kirkbushman.araw.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * This class represent the current logged in user.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname fullname of this class.
 *
 * @property created The unix-time Long representing the creation date of the comment.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the comment.
 *
 * @property hasMail Whether the user has unread mail.
 *
 * @property hasModMail Whether the user has unread mod mail.
 *
 * @property commentKarma karma obtained through submitting a comment.
 *
 * @property linkKarma karma obtained through submitting a post (Submission).
 *
 * @property hasVerifiedEmail if the user has a verified email.
 *
 * @property inboxCount Number of unread messages in the inbox.
 *
 * @property isEmployee if the user is an employee of Reddit.
 *
 * @property isGold if the user has a gold status.
 *
 * @property isMod if the user is Mod.
 *
 * @property over18 whether this account is set to be over 18.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Me(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "coins")
    val coins: Int,

    @Json(name = "comment_karma")
    override val commentKarma: Int,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "has_mail")
    val hasMail: Boolean,

    @Json(name = "has_mod_mail")
    val hasModMail: Boolean,

    @Json(name = "has_verified_email")
    override val hasVerifiedEmail: Boolean,

    @Json(name = "inbox_count")
    val inboxCount: Int,

    @Json(name = "is_employee")
    override val isEmployee: Boolean,

    @Json(name = "is_gold")
    override val isGold: Boolean,

    @Json(name = "hide_from_robots")
    override val isHidingFromRobots: Boolean,

    @Json(name = "is_mod")
    override val isMod: Boolean,

    @Json(name = "is_sponsor")
    val isSponsor: Boolean,

    @Json(name = "is_suspended")
    val isSuspended: Boolean,

    @Json(name = "link_karma")
    override val linkKarma: Int,

    @Json(name = "num_friends")
    val numFriends: Int,

    @Json(name = "subreddit")
    override val subreddit: RedditorSubreddit?,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "verified")
    val verified: Boolean

) : Account
