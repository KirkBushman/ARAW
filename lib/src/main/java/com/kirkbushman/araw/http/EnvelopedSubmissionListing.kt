package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.Envelope
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.http.listings.SubmissionListing
import com.squareup.moshi.Json

data class EnvelopedSubmissionListing(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: SubmissionListing

) : Envelope<SubmissionListing>
