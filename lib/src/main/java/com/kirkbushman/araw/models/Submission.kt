package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.general.Gildings
import com.kirkbushman.araw.models.general.Media
import com.kirkbushman.araw.models.general.MediaEmbed
import com.kirkbushman.araw.models.general.RedditMedia
import com.kirkbushman.araw.models.general.SubmissionPreview
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Distinguishable
import com.kirkbushman.araw.models.mixins.Editable
import com.kirkbushman.araw.models.mixins.Gildable
import com.kirkbushman.araw.models.mixins.Votable
import com.kirkbushman.araw.utils.vote
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@JsonClass(generateAdapter = true)
@Parcelize
data class Submission(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

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
    override val distinguishedRaw: String?,

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

    @Json(name = "is_reddit_media_domain")
    val isRedditMediaDomain: Boolean,

    @Json(name = "is_robot_indexable")
    val isRobotIndexable: Boolean,

    @Json(name = "saved")
    val isSaved: Boolean,

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

    @Json(name = "secure_media")
    val media: Media?,

    @Json(name = "secure_media_embed")
    val mediaEmbed: MediaEmbed,

    @Json(name = "media")
    val redditMedia: RedditMedia?,

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

    @Json(name = "score")
    override val score: Int,

    @Json(name = "selftext")
    val selfText: String?,

    @Json(name = "selftext_html")
    val selfTextHtml: String?,

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

) : Contribution, Votable, Created, Editable, Distinguishable, Gildable, Parcelable {

    fun withClient(client: RedditClient): RedditClient.ContributionHandler {
        return client.contributionHandler(this)
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
                "fullname: $fullname, " +
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
                "isRedditMediaDomain: $isRedditMediaDomain, " +
                "isRobotIndexable: $isRobotIndexable, " +
                "isSelf: $isSelf, " +
                "isSpoiler: $isSpoiler, " +
                "isStickied: $isStickied, " +
                "isVideo: $isVideo, " +
                "likes: $likes, " +
                "numCrossposts: $numCrossposts, " +
                "numComments: $numComments, " +
                "over18: $over18, " +
                "permalink: $permalink, " +
                "preview: $preview, " +
                "saved: $isSaved, " +
                "score: $score, " +
                "selftext: $selfText, " +
                "selftextHtml: $selfTextHtml, " +
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