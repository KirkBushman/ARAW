package com.kirkbushman.araw.models

import com.kirkbushman.araw.RedditClient
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Me(

    @Json(name = "id")
    override val id: String,

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

    @Json(name = "is_mod")
    override val isMod: Boolean,

    @Json(name = "link_karma")
    override val linkKarma: Int,

    @Json(name = "name")
    override val name: String,

    @Json(name = "over_18")
    val over18: Boolean

) : Account {

    fun withClient(client: RedditClient): RedditClient.SelfAccountHandler {
        return client.selfAccountHadler(this)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is Me) {
            return false
        }

        return id == other.id
    }

    override fun toString(): String {
        return "Me { " +
                "id: $id, " +
                "commentKarma: $commentKarma, " +
                "created: $created, " +
                "createdUtc: $createdUtc, " +
                "hasMail: $hasMail, " +
                "hasModMail: $hasModMail, " +
                "hasVerifiedEmail: $hasVerifiedEmail, " +
                "inboxCount: $inboxCount, " +
                "isEmployee: $isEmployee, " +
                "isGold: $isGold," +
                "isMod: $isMod, " +
                "linkKarma: $linkKarma, " +
                "name: $name, " +
                "over18: $over18 " +
                "}"
    }
}
