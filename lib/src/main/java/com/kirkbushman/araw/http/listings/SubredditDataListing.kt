package com.kirkbushman.araw.http.listings

import com.kirkbushman.araw.http.EnvelopedSubredditData
import com.kirkbushman.araw.http.base.Listing
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SubredditDataListing(

    @Json(name = "modhash")
    override val modhash: String?,
    @Json(name = "dist")
    override val dist: Int?,

    @Json(name = "children")
    override val children: List<EnvelopedSubredditData>,

    @Json(name = "after")
    override val after: String?,
    @Json(name = "before")
    override val before: String?

) : Listing<EnvelopedSubredditData>
