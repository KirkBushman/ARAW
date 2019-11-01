package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedContribution
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.ContributionListing
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.mixins.Contribution

class ContributionsFetcher(

    private val api: RedditApi,
    private val username: String,
    private val where: String,

    limit: Int = DEFAULT_LIMIT,

    private var sorting: ContributionsSorting = DEFAULT_SORTING,
    private var timePeriod: TimePeriod = DEFAULT_TIMEPERIOD,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Contribution, EnvelopedContribution>(limit) {

    companion object {
        val DEFAULT_SORTING = ContributionsSorting.NEW
        val DEFAULT_TIMEPERIOD = TimePeriod.ALL_TIME
    }

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedContribution>? {

        val req = if (where == "") {

            api.fetchRedditorOverview(
                username = username,
                sorting = sorting.sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
                limit = if (forward) getLimit() else getLimit() + 1,
                count = getCount(),
                after = if (forward) dirToken else null,
                before = if (!forward) dirToken else null,
                header = getHeader()
            )
        } else {

            api.fetchRedditorInfo(
                username = username,
                where = where,
                sorting = sorting.sortingStr,
                timePeriod = if (getSorting().requiresTimePeriod) getTimePeriod().timePeriodStr else null,
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

    fun getSorting(): ContributionsSorting {
        return sorting
    }

    fun setSorting(newSorting: ContributionsSorting) {
        sorting = newSorting

        reset()
    }

    fun getTimePeriod(): TimePeriod {
        return timePeriod
    }

    fun setTimePeriod(newTimePeriod: TimePeriod) {
        timePeriod = newTimePeriod

        reset()
    }

    fun requiresTimePeriod(): Boolean {
        return getSorting().requiresTimePeriod
    }
}
