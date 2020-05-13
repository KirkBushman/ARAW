package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.ContributionsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.SubredditsFetcher
import com.kirkbushman.araw.models.Friend
import com.kirkbushman.araw.models.Karma
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.Prefs
import com.kirkbushman.araw.models.Trophy
import com.kirkbushman.araw.models.general.ContributionsSorting
import com.kirkbushman.araw.models.general.TimePeriod

class AccountsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    private var currentUser: Me? = null

    fun getCurrentUser(): Me? {

        if (currentUser == null) {
            currentUser = me()
        }

        return currentUser
    }

    fun me(): Me? {

        val authMap = getHeaderMap()
        val req = api.me(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        val me = res.body()
        currentUser = me
        return me
    }

    // todo check why it is not working
    fun myBlocked(): Any? {

        val authMap = getHeaderMap()
        val req = api.myBlocked(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun myFriends(): List<Friend>? {

        val authMap = getHeaderMap()
        val req = api.myFriends(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun myKarma(): List<Karma>? {

        val authMap = getHeaderMap()
        val req = api.myKarma(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun myPrefs(): Prefs? {

        val authMap = getHeaderMap()
        val req = api.myPrefs(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun myTrophies(): List<Trophy>? {

        val authMap = getHeaderMap()
        val req = api.myTrophies(header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.trophies?.map { it.data }?.toList()
    }

    fun overview(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun submitted(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "submitted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun comments(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "comments",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun saved(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "saved",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun hidden(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "hidden",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun upvoted(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "upvoted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun downvoted(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "downvoted",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun gilded(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: ContributionsSorting = ContributionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = ContributionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): ContributionsFetcher {

        return ContributionsFetcher(

            api = api,
            getUsername = { getCurrentUser()!!.fullname },
            where = "gilded",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun subscribedSubreddits(limit: Int = Fetcher.DEFAULT_LIMIT, disableLegacyEncoding: Boolean = false): SubredditsFetcher {
        return SubredditsFetcher(api, "subscriber", limit, disableLegacyEncoding, getHeaderMap)
    }

    fun contributedSubreddits(limit: Int = Fetcher.DEFAULT_LIMIT, disableLegacyEncoding: Boolean = false): SubredditsFetcher {
        return SubredditsFetcher(api, "contributor", limit, disableLegacyEncoding, getHeaderMap)
    }

    fun moderatedSubreddits(limit: Int = Fetcher.DEFAULT_LIMIT, disableLegacyEncoding: Boolean = false): SubredditsFetcher {
        return SubredditsFetcher(api, "moderator", limit, disableLegacyEncoding, getHeaderMap)
    }
}
