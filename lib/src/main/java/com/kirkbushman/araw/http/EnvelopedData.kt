package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.Thing

interface EnvelopedData {

    val kind: EnvelopeKind

    val data: Thing
}
