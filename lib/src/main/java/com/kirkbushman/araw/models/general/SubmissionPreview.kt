package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmissionPreview(

    @Json(name = "images")
    val images: Array<Images>,

    @Json(name = "enabled")
    val isEnabled: Boolean

) : Parcelable {

    fun id(): String? {

        if (images.isEmpty()) {
            return null
        }

        return images.first().id
    }

    fun source(): ImageDetail? {

        if (images.isEmpty()) {
            return null
        }

        return images.first().source
    }

    fun lowerRes(): ImageDetail? {

        if (images.isEmpty()) {
            return null
        }

        return images.first().resolutions.first()
    }

    fun higherRes(): ImageDetail? {

        if (images.isEmpty()) {
            return null
        }

        return images.first().resolutions.last()
    }
}
