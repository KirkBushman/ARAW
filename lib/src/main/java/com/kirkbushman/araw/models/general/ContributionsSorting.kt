package com.kirkbushman.araw.models.general

enum class ContributionsSorting(

    override val requiresTimePeriod: Boolean = false,
    override val sortingStr: String

) : Sorting {

    HOT(sortingStr = "hot"),
    NEW(sortingStr = "new"),
    CONTROVERSIAL(true, "controversial"),
    TOP(true, "top")
}