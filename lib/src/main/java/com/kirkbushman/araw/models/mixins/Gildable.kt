package com.kirkbushman.araw.models.mixins

import com.kirkbushman.araw.models.general.Gildings

/**
 * Base interface for all the item that can be given Gildings
 *
 * @property canGild whether this item can be given a gilding or not.
 *
 * @property gildings the number of times this comment received reddit gold, silver, platinum.
 *
 */
interface Gildable {

    val canGild: Boolean

    val gildings: Gildings
}
