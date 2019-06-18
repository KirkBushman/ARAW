package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedContribution
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.ContributionListing
import com.kirkbushman.araw.models.mixins.Contribution

class ContributionFetcher(

    private val api: RedditApi,
    private val username: String,
    private val where: String,

    limit: Int = DEFAULT_LIMIT,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Contribution, EnvelopedContribution>(limit) {

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedContribution>? {

        val req = if (where == "") {

            api.fetchUserOverview(
                username = username,
                limit = if (forward) getLimit() else getLimit() + 1,
                count = getCount(),
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
                header = getHeader()
            )
        } else {

            api.fetchUserInfo(
                username = username,
                where = where,
                limit = if (forward) getLimit() else getLimit() + 1,
                count = getCount(),
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
                header = getHeader()
            )
        }

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedContribution>?): List<Contribution> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as ContributionListing)
            .children
            .map { it.data }
            .toList()
    }
}