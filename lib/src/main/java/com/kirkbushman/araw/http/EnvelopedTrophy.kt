package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.Envelope
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.Trophy
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedTrophy(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: Trophy

) : Envelope<Trophy>, Parcelable
