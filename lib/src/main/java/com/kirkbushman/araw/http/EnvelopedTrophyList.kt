package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.TrophyList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedTrophyList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: TrophyList

) : Parcelable
