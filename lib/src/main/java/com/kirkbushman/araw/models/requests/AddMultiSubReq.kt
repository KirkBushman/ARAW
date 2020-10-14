package com.kirkbushman.araw.models.requests

import android.os.Parcelable
import com.kirkbushman.araw.models.MultiSub
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AddMultiSubReq(

    @Json(name = "model")
    val model: MultiSub

) : Parcelable
