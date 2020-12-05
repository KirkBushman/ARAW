package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.base.RedditorData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SuspendedRedditor(

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "awarder_karma")
    val awarderKarma: Int?,

    @Json(name = "awardee_karma")
    val awardeeKarma: Int?,

    @Json(name = "is_suspended")
    val isSuspended: Boolean,

    @Json(name = "total_karma")
    val totalKarma: Int

) : RedditorData, Parcelable
