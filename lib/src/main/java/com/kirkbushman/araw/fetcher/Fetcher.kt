package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.http.base.Listing

/**
 * The base class for fetching pages of items
 * in a listing return.
 *
 * @param limit the number of items to return per page.
 * The default is 25, and you can change this to a number from 0 to 100.
 */
abstract class Fetcher<T, E>(

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    private var limit: Long = DEFAULT_LIMIT

) {

    companion object {

        const val DEFAULT_LIMIT = 25L
        const val MIN_LIMIT = 1L
        const val MAX_LIMIT = 100L
    }

    private var currentPage: Int? = null
    private var itemsCount = 0

    private var currentAfter: String? = null
    private var currentBefore: String? = null

    @WorkerThread
    abstract fun onFetching(forward: Boolean = true, dirToken: String?): Listing<E>?
    abstract fun onMapResult(pagedData: Listing<E>?): List<T>

    /**
     * Fetch the next page of content.
     * @return a list of T items.
     */
    @WorkerThread
    fun fetchNext(): List<T> {

        val pagedData = onFetching(true, getNextToken() ?: "")

        currentPage = (currentPage ?: 0) + 1
        itemsCount = limit.toInt() * (currentPage ?: 0)

        currentAfter = pagedData?.after
        currentBefore = pagedData?.before

        return onMapResult(pagedData)
    }

    /**
     * Fetch the previous page of content.
     * @return a list of T items.
     */
    @WorkerThread
    fun fetchPrevious(): List<T>? {

        if (!hasStarted() || !hasPrevious()) {
            return null
        }

        val pagedData = onFetching(false, getPreviousToken())

        currentPage = if (currentPage != null) {
            currentPage!! - 1
        } else {
            null
        }

        itemsCount = limit.toInt() * (currentPage ?: 0)

        currentAfter = pagedData?.after
        currentBefore = pagedData?.before

        return onMapResult(pagedData)
    }

    fun hasStarted(): Boolean {
        return currentPage != null && currentPage!! > 0
    }

    open fun getPreviousToken(): String? {
        return currentBefore
    }

    open fun getNextToken(): String? {
        return currentAfter
    }

    open fun hasNext(): Boolean {
        return currentAfter != null
    }

    open fun hasPrevious(): Boolean {
        return getPreviousToken() != null
    }

    /**
     * Returns the number-of-items-per-page.
     * Is a number from 1 to 100.
     */
    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    fun getLimit(): Long {
        return limit
    }

    /**
     * Change the number of items per page,
     * this will reset the fetcher.
     */
    fun setLimit(@IntRange(from = MIN_LIMIT, to = MAX_LIMIT) newLimit: Long) {
        limit = newLimit

        reset()
    }

    /**
     * Returns the current page number.
     */
    fun getPageNum(): Int? {
        return currentPage
    }

    /**
     * Returns the number of items fetched till now.
     * Equal to number-per-page by the number-of-pages.
     */
    fun getCount(): Int {
        return itemsCount
    }

    /**
     * Reset the settings and start
     * back from the first page of content.
     */
    fun reset() {
        currentPage = null
        itemsCount = 0

        currentAfter = null
        currentBefore = null
    }
}
