package com.kirkbushman.araw.models.enums

import com.kirkbushman.araw.models.base.Sorting

enum class SubredditSearchSorting(

    override val requiresTimePeriod: Boolean = false,
    override val sortingStr: String

) : Sorting {

    RELEVANCE(false, "relevance"),
    ALL(false, "all")
}
