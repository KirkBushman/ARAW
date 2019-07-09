package com.kirkbushman.araw.models.general

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