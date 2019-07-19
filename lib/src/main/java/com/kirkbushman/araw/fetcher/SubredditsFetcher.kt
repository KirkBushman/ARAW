package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.SubredditListing
import com.kirkbushman.araw.models.Subreddit

class SubredditsFetcher(

    private val api: RedditApi,
    private val where: String,

    limit: Int = DEFAULT_LIMIT,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Subreddit, EnvelopedSubreddit>(limit) {

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedSubreddit>? {

        val req = api.fetchUserSubreddits(
            where = where,
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

    override fun onMapResult(pagedData: Listing<EnvelopedSubreddit>?): List<Subreddit> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as SubredditListing)
            .children
            .map { it.data }
            .toList()
    }
}
