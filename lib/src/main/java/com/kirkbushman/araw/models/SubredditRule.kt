package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * This class is used to represent a rule element in a Subreddit,
 * the rules are made by mods, and are expressing good/bad behaviour in the sub.
 *
 * @property kind The scope of the rule.
 *
 * @property description Description of the rule, formatted in Markdown.
 *
 * @property descriptionHtml Description of the rule, formatted in Html.
 *
 * @property shortName Short name of the rule to be used as a title or header.
 *
 * @property violationReason Reasons of bad beaviour, breaking the rule.
 *
 * @property priority The position of this rule in relation of other rules in the
 * Subreddit list.
 *
 * @property createdUtc Date of the creation of this rule, in UTC time.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditRule(

    @Json(name = "kind")
    val kind: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "description_html")
    val descriptionHtml: String?,

    @Json(name = "short_name")
    val shortName: String,

    @Json(name = "violation_reason")
    val violationReason: String,

    @Json(name = "priority")
    val priority: Int,

    @Json(name = "created_utc")
    val createdUtc: Long

) : Parcelable {

    val createdDate: Date
        get() {
            val milliseconds = createdUtc / 1000L
            return Date(milliseconds)
        }
}
