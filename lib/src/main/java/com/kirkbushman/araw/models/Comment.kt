package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.EnvelopedCommentDataListing
import com.kirkbushman.araw.models.general.Gildings
import com.kirkbushman.araw.models.mixins.CommentData
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

@JsonClass(generateAdapter = true)
@Parcelize
data class Comment(

    @Json(name = "id")
    override val id: String,

    @Json(name = "author")
    val author: String,

    @Json(name = "body")
    val body: String,

    @Json(name = "body_html")
    val bodyHtml: String,

    @Json(name = "can_gild")
    override val canGild: Boolean,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "edited")
    override val editedRaw: @RawValue Any,

    @Json(name = "distinguished")
    override val distinguishedRaw: String?,

    @Json(name = "archived")
    val isArchived: Boolean,

    @Json(name = "saved")
    val isSaved: Boolean,

    @Json(name = "stickied")
    val isStickied: Boolean,

    @Json(name = "is_submitter")
    val isSubmitter: Boolean,

    @Json(name = "likes")
    override val likes: Boolean?,

    @Json(name = "link_author")
    val linkAuthor: String?,

    @Json(name = "link_id")
    val linkId: String?,

    @Json(name = "link_url")
    val linkUrl: String?,

    @Json(name = "link_permalink")
    val linkPermalink: String?,

    @Json(name = "gildings")
    override val gildings: Gildings,

    @Json(name = "name")
    val name: String,

    @Json(name = "parent_id")
    val parentId: String,

    @Json(name = "permalink")
    val permalink: String,

    @Json(name = "replies")
    val repliesRaw: EnvelopedCommentDataListing?,

    @Json(name = "score")
    override val score: Int,

    @Json(name = "subreddit")
    val subreddit: String,

    @Json(name = "subreddit_id")
    val subredditId: String,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String

) : CommentData, Votable, Created, Editable, Distinguishable, Gildable, Parcelable, Serializable {

    val hasReplies: Boolean
        get() {

            if (repliesRaw == null) {
                return false
            }

            return repliesRaw.data.children.isNotEmpty()
        }

    val replies: List<CommentData>
        get() {

            if (repliesRaw == null) {
                return listOf()
            }

            return repliesRaw.data.children.map { it.data }.toList()
        }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is Comment) {
            return false
        }

        return id == other.id
    }

    override fun toString(): String {
        return "Comment { " +
                "id: $id, " +
                "author: $author, " +
                "body: $body, " +
                "bodyHtml: $bodyHtml, " +
                "canGild: $canGild, " +
                "created: $created, " +
                "createdUtc: $createdUtc, " +
                "editedRaw: $editedRaw, " +
                "rawDistinguished: $distinguishedRaw, " +
                "isArchived: $isArchived, " +
                "isSaved: $isSaved, " +
                "isStickied: $isStickied, " +
                "isSubmitter: $isSubmitter, " +
                "likes: $likes, " +
                "linkAuthor: $linkAuthor, " +
                "linkId: $linkId, " +
                "linkUrl: $linkUrl, " +
                "linkPermalink: $linkPermalink, " +
                "gildings: $gildings, " +
                "name: $name, " +
                "parentId: $parentId, " +
                "permalink: $permalink, " +
                "score: $score, " +
                "subreddit: $subreddit, " +
                "subredditId: $subredditId, " +
                "subredditNamePrefixed: $subredditNamePrefixed " +
                "}"
    }
}