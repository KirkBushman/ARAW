package com.kirkbushman.araw.models.general

enum class Sorting(val requiresTimePeriod: Boolean = false) {

    HOT,
    BEST,
    NEW,
    RISING,
    CONTROVERSIAL(true),
    TOP(true)
}