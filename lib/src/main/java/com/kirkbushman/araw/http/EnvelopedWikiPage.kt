package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.Envelope
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.WikiPage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedWikiPage(

    @Json(name = "kind")
    override val kind: EnvelopeKind?,

    @Json(name = "data")
    override val data: WikiPage?,

    @Json(name = "reason")
    val reason: String? = null,

    @Json(name = "message")
    val message: String? = null

) : Envelope<WikiPage>, Parcelable
