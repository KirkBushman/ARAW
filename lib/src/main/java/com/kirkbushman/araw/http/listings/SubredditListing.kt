package com.kirkbushman.araw.http.listings

import android.os.Parcelable
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.Listing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditListing(

    @Json(name = "modhash")
    override val modhash: String?,
    @Json(name = "dist")
    override val dist: Int?,

    @Json(name = "children")
    override val children: List<EnvelopedSubreddit>,

    @Json(name = "after")
    override val after: String?,
    @Json(name = "before")
    override val before: String?

) : Listing<EnvelopedSubreddit>, Parcelable
