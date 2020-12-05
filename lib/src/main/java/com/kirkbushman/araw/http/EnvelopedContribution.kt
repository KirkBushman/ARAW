package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.Contribution
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
open class EnvelopedContribution(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: Contribution

) : EnvelopedData(kind, data), Parcelable
