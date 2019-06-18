package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Gildings might be null, even if the object exists
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

) : Parcelable, Serializable {

    fun hasGildings(): Boolean {
        return silverCount != null ||
                goldCount != null ||
                platinumCount != null
    }
}
