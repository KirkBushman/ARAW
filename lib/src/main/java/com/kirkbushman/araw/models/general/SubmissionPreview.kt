package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmissionPreview(

    @Json(name = "images")
    val images: Array<Images>,

    @Json(name = "enabled")
    val isEnabled: Boolean

) : Parcelable, Serializable {

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

    override fun hashCode(): Int {
        return id().hashCode()
    }

    override fun equals(other: Any?): Boolean {

        if (other == null) {
            return false
        }

        if (other !is SubmissionPreview) {
            return false
        }

        return id() == other.id()
    }
}