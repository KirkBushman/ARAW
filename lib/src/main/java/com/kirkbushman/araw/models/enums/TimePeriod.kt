package com.kirkbushman.araw.models.enums

/**
 * This enum defines the time options for filtering, searching or ordering items in the Reddit Api:
 *
 * @property timePeriodStr the raw string as seen by Reddit.
 *
 */
enum class TimePeriod(val timePeriodStr: String) {

    LAST_HOUR("hour"),
    LAST_DAY("day"),
    LAST_WEEK("week"),
    LAST_MONTH("month"),
    LAST_YEAR("year"),
    ALL_TIME("all")
}
