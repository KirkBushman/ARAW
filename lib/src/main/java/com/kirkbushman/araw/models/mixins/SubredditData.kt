package com.kirkbushman.araw.models.mixins

interface SubredditData : Thing, Created {

    override val id: String

    override val fullname: String

    override val created: Long

    override val createdUtc: Long

    val displayName: String

    val displayNamePrefixed: String

    val subredditType: String

    val title: String

    val url: String
}
