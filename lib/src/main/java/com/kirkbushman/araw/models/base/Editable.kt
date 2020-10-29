package com.kirkbushman.araw.models.base

/**
 * Base interface to keep track of when a Thing was edited.
 *
 * @property editedRaw false if not edited, edit date in UTC epoch-seconds otherwise.
 * NOTE: for some old edited comments on reddit.com, this will be set to true instead of edit date.
 * Since this field can be a Boolean or a Long value, it's kept as Any, use the field in the Editable
 * interface to get a nullable Long version, which is more practical to use.
 *
 */
interface Editable {

    val editedRaw: Any
}
