package com.kirkbushman.araw.models.responses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmitResponse(

    @Json(name = "json")
    val json: SubmitResponseJson

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmitResponseJson(

    @Json(name = "errors")
    val errors: List<List<String>>?,

    @Json(name = "data")
    val data: SubmitResponseData?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmitResponseData(

    @Json(name = "user_submitted_page")
    val userSubmittedPage: String?,

    @Json(name = "websocket_url")
    val webSocketUrl: String?

) : Parcelable
