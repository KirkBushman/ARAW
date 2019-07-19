package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.mixins.Contribution
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
open class EnvelopedContribution(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: Contribution

) : EnvelopedData(kind, data), Serializable
