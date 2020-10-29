package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.Envelope
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.SubredditData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedSubredditData(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: SubredditData

) : Envelope<SubredditData>, EnvelopedData(kind, data), Parcelable
