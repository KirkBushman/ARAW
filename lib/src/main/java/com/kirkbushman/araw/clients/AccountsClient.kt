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
    private inline val getHeaderMap: () -> Map<String, String>

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

        return res.body()?.data?.trophies?.map { it.data }
    }

    fun createOverviewContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_OVERVIEW,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createSubmittedContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_SUBMITTED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createCommentsContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_COMMENTS,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createSavedContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_SAVED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createHiddenContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_HIDDEN,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createUpvotedContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_UPVOTED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createDownvotedContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_DOWNVOTED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createGildedContributionsFetcher(

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): ContributionsFetcher {
        return createContributionsFetcher(
            where = ContributionsFetcher.USER_CONTRIB_GILDED,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod
        )
    }

    fun createContributionsFetcher(

        where: String,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

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

    fun createSubscribedSubredditsFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {
        return createSubredditsFetcher(
            where = SubredditsFetcher.SUBREDDITS_MINE_SUBSCRIBER,
            limit = limit
        )
    }

    fun createContributedSubredditsFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {
        return createSubredditsFetcher(
            where = SubredditsFetcher.SUBREDDITS_MINE_CONTRIBUTOR,
            limit = limit
        )
    }

    fun createModeratedSubredditsFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {
        return createSubredditsFetcher(
            where = SubredditsFetcher.SUBREDDITS_MINE_MODERATOR,
            limit = limit
        )
    }

    fun createStreamsSubredditsFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): SubredditsFetcher {
        return createSubredditsFetcher(
            where = SubredditsFetcher.SUBREDDITS_MINE_STREAMS,
            limit = limit
        )
    }

    fun createSubredditsFetcher(

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
