package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedTrophyList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: TrophyList

) : Parcelable, Serializable
