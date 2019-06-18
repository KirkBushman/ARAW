package com.kirkbushman.araw.http

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Parcelize
data class TrophyList(

    @Json(name = "trophies")
    val trophies: List<EnvelopedTrophy>

) : Parcelable, Serializable