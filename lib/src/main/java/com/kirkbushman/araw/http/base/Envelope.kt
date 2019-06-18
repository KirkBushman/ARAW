package com.kirkbushman.araw.http.base

interface Envelope<T> {

    val kind: EnvelopeKind

    val data: T
}