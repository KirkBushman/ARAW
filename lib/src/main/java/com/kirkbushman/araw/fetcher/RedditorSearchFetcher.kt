package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.RedditorListing
import com.kirkbushman.araw.models.Redditor
import com.kirkbushman.araw.models.general.RedditorSearchSorting

class RedditorSearchFetcher(

    private val api: RedditApi,
    private val query: String,

    private val show: String? = null,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: RedditorSearchSorting = DEFAULT_SORTING,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Redditor, EnvelopedRedditor>(limit) {

    companion object {

        val DEFAULT_SORTING = RedditorSearchSorting.RELEVANCE
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedRedditor>? {

        val req = api.fetchRedditorSearch(
            query = query,
            show = show,
            sorting = getSorting().sortingStr,
            limit = if (forward) getLimit() else getLimit() + 1,
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedRedditor>?): List<Redditor> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as RedditorListing)
            .children
            .map { it.data }
            .toList()
    }

    fun getSorting(): RedditorSearchSorting = sorting
    fun setSorting(newSorting: RedditorSearchSorting) {
        sorting = newSorting

        reset()
    }
}
