package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.EnvelopedTrophy
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class TrophyList(

    @Json(name = "trophies")
    val trophies: List<EnvelopedTrophy>

) : Parcelable
