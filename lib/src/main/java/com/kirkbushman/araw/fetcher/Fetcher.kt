package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.http.base.Listing

abstract class Fetcher<T, E>(

    private var limit: Int

) {

    companion object {
        const val DEFAULT_LIMIT = 25
    }

    private var currentPage = 0
    private var itemsCount = 0

    private var currentAfter = ""
    private var currentBefore = ""

    abstract fun onFetching(forward: Boolean = true, dirToken: String): Listing<E>?
    abstract fun onMapResult(pagedData: Listing<E>?): List<T>

    fun fetchNext(): List<T> {
        val pagedData = onFetching(true, currentAfter)

        currentPage++
        itemsCount = limit * currentPage

        currentAfter = pagedData?.after ?: ""
        currentBefore = pagedData?.before ?: ""

        return onMapResult(pagedData)
    }

    fun fetchPrevious(): List<T> {

        if (!hasStarted() || !hasPrevious()) {
            return listOf()
        }

        val pagedData = onFetching(false, currentBefore)

        currentPage--
        itemsCount = limit * currentPage

        currentAfter = pagedData?.after ?: ""
        currentBefore = pagedData?.before ?: ""

        return onMapResult(pagedData)
    }

    fun hasStarted(): Boolean {
        return currentPage > 0
    }

    fun hasNext(): Boolean {

        if (!hasStarted()) {
            return false
        }

        return currentAfter != ""
    }

    fun hasPrevious(): Boolean {

        if (!hasStarted()) {
            return false
        }

        return currentBefore != ""
    }

    fun getLimit(): Int {
        return limit
    }

    fun getPageNum(): Int {
        return currentPage
    }

    fun getCount(): Int {
        return itemsCount
    }

    fun setLimit(newLimit: Int) {
        limit = newLimit

        reset()
    }

    fun reset() {
        currentPage = 0
        itemsCount = 0

        currentAfter = ""
        currentBefore = ""
    }

    override fun toString(): String {
        return "limit: $limit, itemsCount: $itemsCount, currentPage: $currentPage, currentBefore: $currentBefore, currentAfter: $currentAfter"
    }
}
