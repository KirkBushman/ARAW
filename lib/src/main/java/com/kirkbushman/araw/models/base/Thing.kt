package com.kirkbushman.araw.models.base

import android.os.Parcelable

/**
 * Base class for every class of the Reddit API,
 *
 * @property id this item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of the item, e.g. "t1_c3v7f8u"
 *
 */
interface Thing : Parcelable {

    val id: String

    val fullname: String
}
