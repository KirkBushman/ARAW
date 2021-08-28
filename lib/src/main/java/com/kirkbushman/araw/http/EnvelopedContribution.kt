package com.kirkbushman.araw.http

import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.base.Contribution

interface EnvelopedContribution : EnvelopedData {

    override val kind: EnvelopeKind

    override val data: Contribution
}
