package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.EnvelopedTrophy
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * This class represent a trophy given to a redditor.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname Fullname of comment, e.g. "t1_c3v7f8u"
 *
 * @property description description of the trophy, the reason why it was given
 *
 * @property url url of the image at source resolution.
 *
 * @property icon40 url of the image at 40x40 resolution.
 *
 * @property icon70 url of the image at 70x70 resolution.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Trophy(

    @Json(name = "id")
    val id: String?,

    @Json(name = "name")
    val fullname: String?,

    @Json(name = "description")
    val description: String?,

    @Json(name = "icon_70")
    val icon70: String,

    @Json(name = "icon_40")
    val icon40: String,

    @Json(name = "url")
    val url: String?

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class TrophyList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    @Json(name = "data")
    val data: TrophyChildren

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class TrophyChildren(

    @Json(name = "trophies")
    val trophies: List<EnvelopedTrophy>

) : Parcelable
