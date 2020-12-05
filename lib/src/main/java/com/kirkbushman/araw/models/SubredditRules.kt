package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SubredditRules(

    @Json(name = "rules")
    val rules: Array<SubredditRule>,

    @Json(name = "site_rules")
    val siteRules: Array<String>

) : Parcelable {

    override fun hashCode(): Int {
        return rules.contentHashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubredditRules

        if (!rules.contentEquals(other.rules)) return false

        return true
    }
}
