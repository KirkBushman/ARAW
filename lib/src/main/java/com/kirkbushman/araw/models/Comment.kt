package com.kirkbushman.araw.models

import com.kirkbushman.araw.http.EnvelopedCommentDataListing
import com.kirkbushman.araw.models.base.*
import com.kirkbushman.araw.models.commons.FlairRichtext
import com.kirkbushman.araw.models.commons.Gildings
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * This class can represent a comment on a Submission or a reply to another comment.
 *
 * Instances of this class along with the class MoreComments is organized
 * in a tree structure, that composes a Submission comments section.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of comment, e.g. "t1_c3v7f8u"
 *
 * @property author Name of the comment's author
 *
 * @property body The raw text. this is the unformatted text which includes
 * the raw markup characters such as ** for bold. <, >, and & are escaped.
 *
 * @property bodyHtml The formatted HTML text as displayed on reddit. For example,
 * text that is emphasised by * will now have <em> tags wrapping it.
 * Additionally, bullets and numbered lists will now be in HTML list format.
 * NOTE: The HTML string will be escaped. You must unescape to get the raw HTML.
 *
 * @property canGild If the user can give a Gilding to this comment.
 *
 * @property created The unix-time Long representing the creation date of the comment.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the comment.
 *
 * @property editedRaw false if not edited, edit date in UTC epoch-seconds otherwise.
 * NOTE: for some old edited comments on reddit.com, this will be set to true instead of edit date.
 * Since this field can be a Boolean or a Long value, it's kept as Any, use the field in the Editable
 * interface to get a nullable Long version, which is more practical to use.
 *
 * @property depth An integer representing the nested level of this comment.
 *
 * @property distinguishedRaw to allow determining whether they have been distinguished by moderators/admins.
 * null = not distinguished.
 * moderator = the green.
 * admin = the red.
 * special = various other special distinguishes.
 * NOTE: use the extensions in Distinguishable to have an enum representation.
 *
 * @property isArchived whether the comment has been archived or not.
 *
 * @property isLocked whether the comment has been locked or not.
 *
 * @property isSaved whether the comment has been saved by the current user.
 *
 * @property isStickied whether the comment has been stickied at the top of the subreddit or not.
 *
 * @property isSubmitter whether the current user is the submitter of the comment.
 *
 * @property likes how the logged-in user has voted on the comment,
 * True = upvoted, False = downvoted, null = no vote
 * NOTE: use the extensions to have a practical enum class.
 *
 * @property linkTitle title of the Submission related to this comment.
 * Present if the comment is being displayed outside its thread.
 *
 * @property linkAuthor author of the Submission related to this comment.
 * Present if the comment is being displayed outside its thread.
 *
 * @property linkId id of the Submission related to this comment.
 *
 * @property linkUrl url of the Submission related to this comment.
 * Present if the comment is being displayed outside its thread.
 *
 * @property linkPermalink permalink of the Submission related to this comment.
 * Present if the comment is being displayed outside its thread.
 *
 * @property gildings the number of times this comment received reddit gold, silver, platinum.
 *
 * @property parentId ID of the thing this comment is a reply to,
 * either the link or a comment in it.
 *
 * @property parentFullname Fullname of the thing this comment is a reply to,
 * either the link or a comment in it.
 *
 * @property repliesRaw Enveloped models of replies to this comments.
 *
 * @property replies Clear list of the replies to this comment, without envelope.
 *
 * @property score the net-score of the comment.
 *
 * @property subreddit subreddit of the comment excluding the /r/ prefix. e.g. "pics".
 *
 * @property subredditId id of the comment excluding the /r/ prefix.
 *
 * @property subredditNamePrefixed subreddit of the comment including the /r/ prefix.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Comment(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "all_awardings")
    val allAwarding: List<Awarding>?,

    @Json(name = "author")
    val author: String,

    @Json(name = "author_flair_background_color")
    val authorFlairBackgroundColor: String?,

    @Json(name = "author_flair_css_class")
    val authorFlairCssClass: String?,

    @Json(name = "author_flair_richtext")
    val authorFlairRichtext: List<FlairRichtext>?,

    @Json(name = "author_flair_text")
    val authorFlairtext: String?,

    @Json(name = "author_flair_text_color")
    val authorFlairTextColor: String?,

    @Json(name = "author_flair_template_id")
    val authorFlairTemplateId: String?,

    @Json(name = "author_flair_type")
    val authorFlairType: String?,

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

    @Json(name = "depth")
    override val depth: Int = 0,

    @Json(name = "distinguished")
    override val distinguishedRaw: String?,

    @Json(name = "archived")
    val isArchived: Boolean,

    @Json(name = "locked")
    val isLocked: Boolean,

    @Json(name = "saved")
    override val isSaved: Boolean,

    @Json(name = "score_hidden")
    val isScoreHidden: Boolean,

    @Json(name = "stickied")
    val isStickied: Boolean,

    @Json(name = "is_submitter")
    val isSubmitter: Boolean,

    @Json(name = "likes")
    override val likes: Boolean?,

    @Json(name = "link_title")
    val linkTitle: String?,

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

    @Json(name = "parent_id")
    val parentId: String,

    @Json(name = "permalink")
    val permalink: String,

    @Json(name = "replies")
    val repliesRaw: EnvelopedCommentDataListing?,

    @Transient
    override var replies: List<CommentData>? =
        repliesRaw?.data?.children?.map { it.data }?.toList(),

    @Transient
    override val parentFullname: String = parentId,

    @Json(name = "score")
    override val score: Int,

    @Json(name = "subreddit")
    val subreddit: String,

    @Json(name = "subreddit_id")
    val subredditId: String,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String

) : CommentData, Votable, Saveable, Created, Editable, Distinguishable, Gildable, Replyable {

    override val hasReplies: Boolean
        get() {
            return replies != null && replies!!.isNotEmpty()
        }

    override val repliesSize: Int
        get() {
            return replies?.size ?: 0
        }
}
