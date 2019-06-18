package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Distinguishable
import com.kirkbushman.araw.models.mixins.Thing
import com.kirkbushman.araw.models.mixins.Votable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Parcelize
data class Message(

    @Json(name = "id")
    override val id: String,

    @Json(name = "author")
    val author: String?,

    @Json(name = "body")
    val body: String,

    @Json(name = "body_html")
    val bodyHtml: String,

    @Json(name = "context")
    val context: String,

    @Json(name = "created")
    override val created: Long,

    @Json(name = "created_utc")
    override val createdUtc: Long,

    @Json(name = "dest")
    val dest: String,

    @Json(name = "distinguished")
    override val distinguishedRaw: String?,

    @Json(name = "first_message")
    val firstMessage: Long?,

    @Json(name = "first_message_name")
    val firstMessageName: String?,

    @Json(name = "was_comment")
    val isComment: Boolean,

    @Json(name = "new")
    val isUnread: Boolean,

    @Json(name = "likes")
    override val likes: Boolean?,

    @Json(name = "name")
    val name: String,

    @Json(name = "num_comments")
    val numComments: Int?,

    @Json(name = "parent_id")
    val parentId: String?,

    @Json(name = "score")
    override val score: Int,

    @Json(name = "subject")
    val subject: String,

    @Json(name = "subreddit")
    val subreddit: String?,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String?

) : Thing, Votable, Created, Distinguishable, Parcelable, Serializable {

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is Message) {
            return false
        }

        return id == other.id
    }

    override fun toString(): String {
        return "Message { " +
                "id: $id, " +
                "author: $author, " +
                "body: $body, " +
                "bodyHtml: $bodyHtml, " +
                "context: $context, " +
                "created: $created, " +
                "createdUtc: $createdUtc, " +
                "dest: $dest, " +
                "rawDistinguished: $distinguishedRaw, " +
                "firstMessage: $firstMessage, " +
                "firstMessageName: $firstMessageName, " +
                "isComment: $isComment, " +
                "isUnread: $isUnread, " +
                "likes: $likes, " +
                "name: $name, " +
                "numComments: $numComments, " +
                "parentId: $parentId, " +
                "score: $score, " +
                "subject: $subject, " +
                "subreddit: $subreddit, " +
                "subredditNamePrefixed: $subredditNamePrefixed " +
                "}"
    }
}