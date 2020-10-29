package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.Thing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Friend(

    @Json(name = "id")
    override val id: String,

    @Json(name = "name")
    override val fullname: String,

    @Json(name = "rel_id")
    val relId: String,

    @Json(name = "date")
    val added: Long

) : Thing, Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class FriendList(

    @Json(name = "kind")
    val kind: EnvelopeKind,

    val data: FriendListData

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class FriendListData(

    val children: List<Friend>

) : Parcelable
