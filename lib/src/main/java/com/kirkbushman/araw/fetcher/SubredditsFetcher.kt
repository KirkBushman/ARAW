package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubredditData
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubredditDataListing
import com.kirkbushman.araw.models.mixins.SubredditData

class SubredditsFetcher(

    private val api: RedditApi,
    private val where: String,

    limit: Int = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<SubredditData, EnvelopedSubredditData>(limit) {

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubredditData>? {

        val req = api.fetchRedditorSubreddits(
            where = where,
            limit = getLimit(),
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedSubredditData>?): List<SubredditData> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as SubredditDataListing)
            .children
            .map { it.data }
            .toList()
    }
}
