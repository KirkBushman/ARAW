package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.mixins.Thing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class EnvelopedData(

    @Json(name = "kind")
    open val kind: EnvelopeKind,

    @Json(name = "data")
    open val data: Thing

)
