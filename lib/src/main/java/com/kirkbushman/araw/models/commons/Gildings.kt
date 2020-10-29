package com.kirkbushman.araw.models.commons

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * This class represents the amount of silver, gold and platinum that a contribution has,
 * given by users.
 *
 * Gildings might be null, even if the object exists.
 *
 * @property silverCount silver count.
 *
 * @property goldCount gold count.
 *
 * @property platinumCount platinum count.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
class Gildings(

    @Json(name = "gid_1")
    val silverCount: Short?,

    @Json(name = "gid_2")
    val goldCount: Short?,

    @Json(name = "gid_3")
    val platinumCount: Short?

) : Parcelable {

    fun hasGildings(): Boolean {
        return silverCount != null || goldCount != null || platinumCount != null
    }
}
