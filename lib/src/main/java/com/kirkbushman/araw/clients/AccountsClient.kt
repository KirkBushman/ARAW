package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.SubredditsFetcher
import com.kirkbushman.araw.models.Friend
import com.kirkbushman.araw.models.Karma
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.Prefs
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.enums.ContributionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod

class AccountsClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> HashMap<String, String>

) {

    private var currentUser: Me? = null

    fun getCurrentUser(): Me? {

        if (currentUser == null) {
            currentUser = me()
        }

        return currentUser
    }

    @WorkerThread
    fun me(): Me? {

        val authMap = getHeaderMap()
        val req = api.me(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        val me = res.body()
        currentUser = me
        return me
    }

    // todo check why it is not working
    @WorkerThread
    fun myBlocked(): Any? {

        val authMap = getHeaderMap()
        val req = api.myBlocked(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun myFriends(): List<Friend>? {

        val authMap = getHeaderMap()
        val req = api.myFriends(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    @WorkerThread
    fun myKarma(): List<Karma>? {

        val authMap = getHeaderMap()
        val req = api.myKarma(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    @WorkerThread
    fun myPrefs(): Prefs? {

        val authMap = getHeaderMap()
        val req = api.myPrefs(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun myTrophies(): List<Trophy>? {

        val authMap = getHeaderMap()
        val req = api.myTrophies(
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.trophies?.map { it.data }?.toList()
    }

    fun overview(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun submitted(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "submitted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun comments(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "comments",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun saved(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "saved",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun hidden(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "hidden",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun upvoted(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "upvoted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun downvoted(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "downvoted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun gilded(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return fetchContributions(
            where = "gilded",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun fetchContributions(

        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD

    ): ContributionsFetcher {

        return ContributionsFetcher(
            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = where,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun subscribedSubreddits(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {

        return fetchSubreddits(
            where = "subscriber",
            limit = limit
        )
    }

    fun contributedSubreddits(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {

        return fetchSubreddits(
            where = "contributor",
            limit = limit
        )
    }

    fun moderatedSubreddits(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {

        return fetchSubreddits(
            where = "moderator",
            limit = limit
        )
    }

    fun fetchSubreddits(

        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {

        return SubredditsFetcher(
            api = api,
            where = where,
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }
}
