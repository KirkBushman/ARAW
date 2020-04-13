package com.kirkbushman.araw.models.general

/**
 * Base interface to group all sorting options in the Reddit Api
 *
 * @property requiresTimePeriod If this sorting option needs
 * to be combined also with a TimePeriod, to be sent to the Api.
 * e.g. Top / Last Month
 *
 * @property sortingStr raw string as seen by the Api.
 *
 */
interface Sorting {

    val requiresTimePeriod: Boolean

    val sortingStr: String
}
