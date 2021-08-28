package com.kirkbushman.araw.http

import android.os.Parcelable
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.CommentData

interface EnvelopedCommentData : EnvelopedContribution, Parcelable {

    override val kind: EnvelopeKind

    override val data: CommentData
}
