package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteRes(

    @Json(name = "data")
    val data: PollVoteData

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteData(

    @Json(name = "updatePostPollVoteState")
    val updatePostPollVoteState: PollVoteState

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteState(

    @Json(name = "ok")
    val ok: Boolean,

    @Json(name = "poll")
    val poll: PollVoteStatePoll

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteStatePoll(

    @Json(name = "options")
    val options: List<PollVoteStateOption>

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollVoteStateOption(

    @Json(name = "id")
    val id: String,

    @Json(name = "voteCount")
    val voteCount: Int

) : Parcelable
