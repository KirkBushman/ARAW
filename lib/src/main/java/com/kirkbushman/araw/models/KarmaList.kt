package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class KarmaList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: List<Karma>

) : Parcelable
