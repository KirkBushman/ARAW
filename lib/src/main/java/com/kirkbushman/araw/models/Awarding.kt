package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.base.Thing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Awarding(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "award_type")
    val awardType: String,

    @Json(name = "count")
    val count: Int,

    @Json(name = "coin_price")
    val coinPrice: Int,

    @Json(name = "coin_reward")
    val coinReward: Int,

    @Json(name = "description")
    val description: String,

    @Json(name = "icon_url")
    val iconUrl: String,

    @Json(name = "icon_width")
    val iconWidth: Int,

    @Json(name = "icon_height")
    val iconHeight: Int,

    @Json(name = "is_enabled")
    val isEnabled: Boolean

) : Thing, Parcelable
