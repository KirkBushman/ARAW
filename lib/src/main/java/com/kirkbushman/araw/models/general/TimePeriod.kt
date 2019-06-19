package com.kirkbushman.araw.models.general

enum class TimePeriod(val timePeriodStr: String) {

    LAST_HOUR("hour"),
    LAST_DAY("day"),
    LAST_WEEK("week"),
    LAST_MONTH("month"),
    LAST_YEAR("year"),
    ALL_TIME("all")
}