package com.kirkbushman.araw.models.enums

import com.kirkbushman.araw.models.base.Sorting

enum class SearchSorting(

    override val requiresTimePeriod: Boolean = false,
    override val sortingStr: String

) : Sorting {

    RELEVANCE(true, sortingStr = "relevance"),
    HOT(sortingStr = "hot"),
    NEW(sortingStr = "new"),
    TOP(true, "top"),
    COMMENTS(true, "comments")
}
