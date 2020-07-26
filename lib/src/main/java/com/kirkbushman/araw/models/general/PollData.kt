package com.kirkbushman.araw.models.general

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class PollData(

    @Json(name = "user_selection")
    val userSelection: Long?,

    @Json(name = "options")
    val options: List<PollOption>,

    @Json(name = "total_vote_count")
    val totalVoteCount: Int,

    @Json(name = "voting_end_timestamp")
    val votingEndTimestamp: Long

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PollOption(

    @Json(name = "id")
    val id: Long,

    @Json(name = "text")
    val text: String,

    @Json(name = "vote_count")
    val voteCount: Int?

) : Parcelable
