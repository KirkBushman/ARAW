package com.kirkbushman.araw.models.mixins

import android.os.Parcelable

/**
 * Base class for every class of the Reddit API,
 *
 * @property id this item identifier, e.g. "8xwlg"
 */
interface Thing : Parcelable {

    val id: String
}
