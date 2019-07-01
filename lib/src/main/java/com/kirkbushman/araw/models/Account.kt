package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.models.mixins.Created
import com.kirkbushman.araw.models.mixins.Thing

interface Account : Thing, Created, Parcelable {

    override val id: String

    override val created: Long

    override val createdUtc: Long

    val commentKarma: Int

    val hasVerifiedEmail: Boolean

    val isEmployee: Boolean

    val isGold: Boolean

    val isMod: Boolean

    val linkKarma: Int

    val name: String
}