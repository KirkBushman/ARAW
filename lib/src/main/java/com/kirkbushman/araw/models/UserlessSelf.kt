package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@JsonClass(generateAdapter = true)
@Parcelize
data class UserlessSelf(

    @Json(name = "features")
    val features: @RawValue Map<String, Any>

) : Parcelable
