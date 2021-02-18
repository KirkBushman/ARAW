package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread

/**
 * The base class for fetching pages of items
 * in a listing return.
 *
 * @param limit the number of items to return per page.
 * The default is 25, and you can change this to a number from 0 to 100.
 */
abstract class Fetcher<T>(

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    private var limit: Long = DEFAULT_LIMIT

) {

    companion object {

        const val DEFAULT_LIMIT = 25L
        const val MIN_LIMIT = 1L
        const val MAX_LIMIT = 100L
    }

    private var currentPage: Int = -1
    private var itemsCount: Int = 0

    private var nextToken: String? = null
    private var previousToken: String? = null

    @WorkerThread
    abstract fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (next: String?, previous: String?) -> Unit
    ): List<T>?

    /**
     * Fetch the next page of content.
     * @return a list of T items.
     */
    @WorkerThread
    fun fetchNext(): List<T>? {

        val pagedData = onFetching(
            previousToken = null,
            nextToken = nextToken,
            setTokens = { previous, next ->

                previousToken = previous
                nextToken = next
            }
        )

        currentPage = if (currentPage == -1) {
            1
        } else {
            currentPage + 1
        }

        itemsCount = limit.toInt() * currentPage

        return pagedData
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

        val pagedData = onFetching(
            previousToken = previousToken,
            nextToken = null,
            setTokens = { previous, next ->

                previousToken = previous
                nextToken = next
            }
        )

        currentPage = when {
            currentPage < 1 -> -1
            else -> currentPage - 1
        }

        itemsCount = limit.toInt() * currentPage

        return pagedData
    }

    fun hasStarted(): Boolean {
        return currentPage > 0
    }

    fun hasNext(): Boolean {
        return nextToken != null
    }

    fun hasPrevious(): Boolean {
        return previousToken != null
    }

    fun getPreviousToken(): String? {
        return previousToken
    }

    fun getNextToken(): String? {
        return nextToken
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
    fun getPageNum(): Int {
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
        currentPage = -1
        itemsCount = 0

        nextToken = null
        previousToken = null
    }
}
