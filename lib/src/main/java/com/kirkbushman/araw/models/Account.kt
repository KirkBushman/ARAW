package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Thing

/**
 * Base interface that groups the common fields between the class Redditor and Me,
 * that has extra fields of the current logged in user.
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
 * @property isEmployee if the user is an employee of Reddit.
 *
 * @property isGold if the user has a gold status.
 *
 * @property isMod if the user is Mod.
 *
 */
interface Account : Thing, Created, Parcelable {

    override val id: String

    override val fullname: String

    override val created: Long

    override val createdUtc: Long

    val commentKarma: Int

    val hasVerifiedEmail: Boolean

    val isEmployee: Boolean

    val isGold: Boolean

    val isHidingFromRobots: Boolean

    val isMod: Boolean

    val linkKarma: Int
}
