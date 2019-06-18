package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.general.Distinguished
import com.kirkbushman.araw.models.general.Gildings
import com.kirkbushman.araw.models.general.SubmissionPreview
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Distinguishable
import com.kirkbushman.araw.models.mixins.Editable
import com.kirkbushman.araw.models.mixins.Gildable
import com.kirkbushman.araw.models.mixins.Votable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable
import java.util.*

@JsonClass(generateAdapter = true)
@Parcelize
data class Submission(

    @Json(name = "id")
    override val id: String,

    @Json(name = "author")
    val author: String,

    @Json(name = "author_flair_text")
    val authorFlairText: String?,

    @Json(name = "author_fullname")
    val authorFullname: String?,

    @Json(name = "can_gild")
    override val canGild: Boolean,

    @Json(name = "clicked")
    val clicked: Boolean,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "distinguished")
    override val rawDistinguished: String?,

    @Json(name = "domain")
    val domain: String,

    @Json(name = "edited")
    override val editedRaw: @RawValue Any,

    @Json(name = "gildings")
    override val gildings: Gildings,

    @Json(name = "hidden")
    val hidden: Boolean,

    @Json(name = "archived")
    val isArchived: Boolean,

    @Json(name = "locked")
    val isLocked: Boolean,

    @Json(name = "pinned")
    val isPinned: Boolean,

    @Json(name = "is_robot_indexable")
    val isRobotIndexable: Boolean,

    @Json(name = "is_self")
    val isSelf: Boolean,

    @Json(name = "spoiler")
    val isSpoiler: Boolean,

    @Json(name = "stickied")
    val isStickied: Boolean,

    @Json(name = "is_video")
    val isVideo: Boolean,

    @Json(name = "likes")
    override val likes: Boolean?,

    @Json(name = "name")
    val name: String,

    @Json(name = "num_crossposts")
    val numCrossposts: Int,

    @Json(name = "num_comments")
    val numComments: Int,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "permalink")
    val permalink: String,

    @Json(name = "preview")
    val preview: SubmissionPreview?,

    @Json(name = "saved")
    val saved: Boolean,

    @Json(name = "score")
    override val score: Int,

    @Json(name = "selftext")
    val selftext: String?,

    @Json(name = "selftext_html")
    val selftextHtml: String?,

    @Json(name = "subreddit")
    val subreddit: String,

    @Json(name = "subreddit_id")
    val subredditId: String,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String,

    @Json(name = "thumbnail")
    val thumbnailUrl: String?,

    @Json(name = "thumbnail_width")
    val thumbnailWidth: Int?,

    @Json(name = "thumbnail_height")
    val thumbnailHeight: Int?,

    @Json(name = "title")
    val title: String,

    @Json(name = "upvote_ratio")
    val upvoteRatio: Float?,

    @Json(name = "url")
    val url: String

) : Contribution, Votable, Created, Editable, Distinguishable, Gildable, Parcelable, Serializable {

    override val vote: Vote
        get() {
            return when (likes) {
                true -> Vote.UPVOTE
                false -> Vote.DOWNVOTE
                else -> Vote.NONE
            }
        }

    override val distinguished: Distinguished
        get() {
            return when (rawDistinguished) {
                "moderator" -> Distinguished.MODERATOR
                "admin" -> Distinguished.ADMIN
                "special" -> Distinguished.SPECIAL
                else -> Distinguished.NOT_DISTINGUISHED
            }
        }

    override val hasEdited: Boolean
        get() {

            if (editedRaw is Long) {
                return true
            }

            if (editedRaw is Boolean) {
                return editedRaw
            }

            return false
        }

    override val edited: Date
        get() {

            if (editedRaw is Long) {
                val milliseconds = editedRaw / 1000L
                return Date(milliseconds)
            }

            return Date()
        }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is Submission) {
            return false
        }

        return id == other.id
    }

    override fun toString(): String {
        return "Submission { " +
                "id: $id, " +
                "author: $author, " +
                "authorFlairText: $authorFlairText, " +
                "authorFullname: $authorFullname, " +
                "canGild: $canGild, " +
                "clicked: $clicked, " +
                "created: $created, " +
                "createdUtc: $createdUtc, " +
                "domain: $domain, " +
                "editedRaw: $editedRaw, " +
                "gildings: $gildings, " +
                "hidden: $hidden, " +
                "isArchived: $isArchived, " +
                "isLocked: $isLocked, " +
                "isPinned: $isPinned, " +
                "isRobotIndexable: $isRobotIndexable, " +
                "isSelf: $isSelf, " +
                "isSpoiler: $isSpoiler, " +
                "isStickied: $isStickied, " +
                "isVideo: $isVideo, " +
                "likes: $likes, " +
                "name: $name, " +
                "numCrossposts: $numCrossposts, " +
                "numComments: $numComments, " +
                "over18: $over18, " +
                "permalink: $permalink, " +
                "preview: $preview, " +
                "saved: $saved, " +
                "score: $score, " +
                "selftext: $selftext, " +
                "selftextHtml: $selftextHtml, " +
                "subreddit: $subreddit, " +
                "subredditId: $subredditId, " +
                "subredditNamePrefixed: $subredditNamePrefixed, " +
                "thumbnailUrl: $thumbnailUrl, " +
                "thumbnailWidth: $thumbnailWidth, " +
                "thumbnailHeight: $thumbnailHeight, " +
                "title: $title, " +
                "url: $url, " +
                "upvoteRatio: $upvoteRatio, " +
                "vote: $vote " +
                "}"
    }
}