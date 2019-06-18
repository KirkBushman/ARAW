package com.kirkbushman.araw.models

import com.kirkbushman.araw.models.mixins.Thing

interface Account : Thing {

    override val id: String

    val commentKarma: Int

    val hasVerifiedEmail: Boolean

    val isEmployee: Boolean

    val isGold: Boolean

    val isMod: Boolean

    val linkKarma: Int

    val name: String
}