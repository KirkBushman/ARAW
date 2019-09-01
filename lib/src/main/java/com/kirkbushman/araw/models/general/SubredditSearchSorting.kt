package com.kirkbushman.araw.models.general

enum class SubredditSearchSorting(

    override val requiresTimePeriod: Boolean = false,
    override val sortingStr: String

) : Sorting {

    RELEVANCE(false, "relevance"),
    ALL(false, "all")
}
