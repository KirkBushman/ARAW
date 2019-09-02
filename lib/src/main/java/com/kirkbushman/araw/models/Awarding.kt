package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Awarding(

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

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

) : Parcelable {

    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Awarding

        if (id != other.id) return false

        return true
    }
}
