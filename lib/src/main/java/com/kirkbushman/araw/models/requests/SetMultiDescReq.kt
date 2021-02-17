package com.kirkbushman.araw.models.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SetMultiDescReq(

    @Json(name = "body_md")
    val bodyMd: String
)