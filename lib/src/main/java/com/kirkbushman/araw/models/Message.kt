package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Distinguishable
import com.kirkbushman.araw.models.mixins.Replyable
import com.kirkbushman.araw.models.mixins.Thing
import com.kirkbushman.araw.models.mixins.Votable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * This class represents the message a user can send or receive.
 * Messages are grouped in the user inbox.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of message, e.g. "t1_c3v7f8u"
 *
 * @property author Name of the message's author
 *
 * @property body The raw text. this is the unformatted text which includes
 * the raw markup characters such as ** for bold. <, >, and & are escaped.
 *
 * @property bodyHtml The formatted HTML text as displayed on reddit. For example,
 * text that is emphasised by * will now have <em> tags wrapping it.
 * Additionally, bullets and numbered lists will now be in HTML list format.
 * NOTE: The HTML string will be escaped. You must unescape to get the raw HTML.
 *
 * @property context the message is a comment, then the permalink to the comment
 * with ?context=3 appended to the end, otherwise an empty string
 *
 * @property created The unix-time Long representing the creation date of the message.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the message.
 *
 * @property dest The destination of this message.
 *
 * @property distinguishedRaw to allow determining whether they have been distinguished by moderators/admins.
 * null = not distinguished.
 * moderator = the green.
 * admin = the red.
 * special = various other special distinguishes.
 * NOTE: use the extensions in Distinguishable to have an enum representation.
 *
 * @property firstMessage either null or the first message's ID represented as base 10 (wtf).
 *
 * @property firstMessageName either null or the first message's fullname.
 *
 * @property isComment if the message relates to a comment under
 * your submission or a reply to the current logged in comment.
 *
 * @property isUnread if the message is still unread.
 *
 * @property likes how the logged-in user has voted on the comment,
 * True = upvoted, False = downvoted, null = no vote
 * NOTE: use the extensions to have a practical enum class.
 *
 * @property parentId null if no parent is attached.
 *
 * @property replies an empty string if there are no replies.
 *
 * @property subject subject of the message.
 *
 * @property subreddit null if not a comment.
 *
 * @property subredditNamePrefixed subreddit name with the /r/ prefix, null if not a comment.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Message(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

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

) : Thing, Votable, Created, Distinguishable, Replyable, Parcelable
