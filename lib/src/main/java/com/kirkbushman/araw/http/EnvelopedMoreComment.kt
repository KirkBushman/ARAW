package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.Envelope
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.MoreComments
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Parcelize
data class EnvelopedMoreComment(

    @Json(name = "kind")
    override val kind: EnvelopeKind,

    @Json(name = "data")
    override val data: MoreComments

) : Envelope<MoreComments>, EnvelopedCommentData(kind, data), Parcelable, Serializable