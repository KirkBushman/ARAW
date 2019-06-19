package com.kirkbushman.araw.models.general

enum class SubmissionSorting(

    override val requiresTimePeriod: Boolean = false,
    override val sortingStr: String

) : Sorting {

    HOT(sortingStr = "hot"),
    BEST(sortingStr = "best"),
    NEW(sortingStr = "new"),
    RISING(sortingStr = "rising"),
    CONTROVERSIAL(true, "controversial"),
    TOP(true, "top")
}