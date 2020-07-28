package com.kirkbushman.araw.models.mixins

/**
 * Base interface for all the items that can be saved,
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of item, e.g. "t1_c3v7f8u"
 *
 * @property isSaved if this item is saved in the current user account
 *
 */
interface Saveable {

    val id: String

    val fullname: String

    val isSaved: Boolean
}
