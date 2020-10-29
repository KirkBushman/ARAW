package com.kirkbushman.araw.http.base

import com.squareup.moshi.Json

enum class EnvelopeKind(val value: String) {

    @Json(name = "t1") Comment("t1"),
    @Json(name = "t2") Account("t2"),
    @Json(name = "t3") Link("t3"),
    @Json(name = "t4") Message("t4"),
    @Json(name = "t5") Subreddit("t5"),
    @Json(name = "t6") Award("t6"),

    @Json(name = "Listing") Listing("Listing"),
    @Json(name = "wikipage") Wikipage("wikipage"),
    @Json(name = "wikipagelisting") WikipageListing("wikipagelisting"),
    @Json(name = "more") More("more"),
    @Json(name = "TrophyList") TrophyList("TrophyList"),
    @Json(name = "UserList") UserList("UserList"),
    @Json(name = "KarmaList") KarmaList("KarmaList"),
    @Json(name = "ModeratedList") ModeratedList("ModeratedList"),
    @Json(name = "LabeledMulti") LabeledMulti("LabeledMulti"),
    @Json(name = "LabeledMultiDescription") LabeledMultiDescription("LabeledMultiDescription")
}
