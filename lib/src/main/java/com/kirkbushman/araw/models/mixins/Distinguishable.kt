package com.kirkbushman.araw.models.mixins

/**
 * Base interface for all items that can be distingushed
 * by moderators, admins or else.
 *
 * @property distinguishedRaw to allow determining whether they have been distinguished by moderators/admins.
 * null = not distinguished.
 * moderator = the green.
 * admin = the red.
 * special = various other special distinguishes.
 * NOTE: use the extensions in Distinguishable to have an enum representation.
 *
 */
interface Distinguishable {

    val distinguishedRaw: String?
}
