package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.TrophyList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EnvelopedTrophyList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: TrophyList

)
