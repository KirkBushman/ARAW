package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteReq(

    @Json(name = "id")
    val id: String,

    @Json(name = "variables")
    val variables: PollVoteVariables

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteVariables(

    @Json(name = "input")
    val input: PollVoteInput

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteInput(

    @Json(name = "postId")
    val postId: String,

    @Json(name = "optionId")
    val optionId: String

) : Parcelable
