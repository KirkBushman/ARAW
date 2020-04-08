package com.kirkbushman.araw.models.mixins

/**
 * Base interface for keeping track of when a Thing was created.
 *
 * @property created The unix-time Long representing the creation date of the item.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the item.
 *
 */
interface Created {

    val created: Long

    val createdUtc: Long
}
