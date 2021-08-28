package com.kirkbushman.araw.models.responses

import com.kirkbushman.araw.http.EnvelopedCommentData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoreChildrenResponse(

    @Json(name = "json")
    val json: MoreChildrenResponseJSON

)

@JsonClass(generateAdapter = true)
data class MoreChildrenResponseJSON(

    @Json(name = "data")
    val data: MoreChildrenResponseThings

)

@JsonClass(generateAdapter = true)
data class MoreChildrenResponseThings(

    @Json(name = "things")
    val things: List<EnvelopedCommentData>

)
