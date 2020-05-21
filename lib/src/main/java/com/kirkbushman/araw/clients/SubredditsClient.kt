package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.fetcher.SubredditsSearchFetcher
import com.kirkbushman.araw.models.SubmitResponse
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.araw.models.User
import com.kirkbushman.araw.models.general.SubmissionKind
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.SubredditSearchSorting
import com.kirkbushman.araw.models.general.TimePeriod
import java.lang.IllegalStateException

class SubredditsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    fun subreddit(subreddit: String, disableLegacyEncoding: Boolean = false): Subreddit? {

        val authMap = getHeaderMap()
        val req = api.subreddit(
            subreddit = subreddit,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun subreddits(vararg ids: String, disableLegacyEncoding: Boolean = false): List<Subreddit>? {

        val authMap = getHeaderMap()
        val req = api.subreddits(
            subredditIds = ids.joinToString { "t5_$it" },
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children?.map { it.data }
    }

    fun subredditBanned(subreddit: Subreddit): List<User>? {
        return subredditBanned(subreddit.fullname)
    }

    fun subredditBanned(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "banned", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun subredditMuted(subreddit: Subreddit): List<User>? {
        return subredditMuted(subreddit.fullname)
    }

    fun subredditMuted(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "muted", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun subredditWikiBanned(subreddit: Subreddit): List<User>? {
        return subredditWikiBanned(subreddit.fullname)
    }

    fun subredditWikiBanned(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "wikibanned", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun subredditContributors(subreddit: Subreddit): List<User>? {
        return subredditContributors(subreddit.fullname)
    }

    fun subredditContributors(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "contributors", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun subredditWikiContributors(subreddit: Subreddit): List<User>? {
        return subredditWikiContributors(subreddit.fullname)
    }

    fun subredditWikiContributors(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "wikicontributors", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun subredditModerators(subreddit: Subreddit): List<User>? {
        return subredditModerators(subreddit.fullname)
    }

    fun subredditModerators(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(subredditName, "moderators", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }

    fun rules(subreddit: Subreddit): Array<SubredditRule>? {
        return rules(subreddit.displayName)
    }

    fun rules(subreddit: String): Array<SubredditRule>? {

        val authMap = getHeaderMap()
        val req = api.rules(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.rules
    }

    fun frontpage(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun all(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "all",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun popular(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "popular",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun friends(

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsFetcher {

        return SubmissionsFetcher(

            api = api,
            subreddit = "friends",
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun submit(

        subreddit: Subreddit,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean? = null,
        extension: String? = null,
        rawJson: Int? = null,

        sendReplies: Boolean = false,
        submitType: String? = null,
        isNsfw: Boolean = false,
        isSpoiler: Boolean = false,
        isOriginalContent: Boolean = false,

        validateOnSubmit: Boolean? = null,
        showErrorList: Boolean? = null

    ): SubmitResponse? {

        return submit(
            subredditName = subreddit.displayName,

            title = title,
            kind = kind,

            text = text,
            url = url,

            resubmit = resubmit,
            extension = extension,
            rawJson = rawJson,

            sendReplies = sendReplies,
            submitType = submitType,
            isNsfw = isNsfw,
            isSpoiler = isSpoiler,
            isOriginalContent = isOriginalContent,

            validateOnSubmit = validateOnSubmit,
            showErrorList = showErrorList
        )
    }

    fun submit(

        subredditName: String,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean? = null,
        extension: String? = null,
        rawJson: Int? = null,

        sendReplies: Boolean = false,
        submitType: String? = null,
        isNsfw: Boolean = false,
        isSpoiler: Boolean = false,
        isOriginalContent: Boolean = false,

        validateOnSubmit: Boolean? = null,
        showErrorList: Boolean? = null

    ): SubmitResponse? {

        val authMap = getHeaderMap()
        val req = api.submit(
            subreddit = subredditName,

            title = title,
            kind = kind.toString(),

            text = (if (kind == SubmissionKind.self) text else null),
            url = (if (kind != SubmissionKind.self) url else null),

            resubmit = resubmit,
            extension = extension,
            rawJson = rawJson,

            sendReplies = sendReplies,
            submitType = submitType,

            isNsfw = isNsfw,
            isSpoiler = isSpoiler,
            isOriginalContent = isOriginalContent,

            validateOnSubmit = validateOnSubmit,
            showErrorList = showErrorList,

            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun subscribe(subreddit: Subreddit, skipInitialDefaults: Boolean = true): Any? {

        if (subreddit.isSubscriber == null) {
            throw IllegalStateException("isSubscriber is null! Is this a userless grant...?")
        }

        return subscribe(

            subredditNames = listOf(subreddit.displayName),

            action = !subreddit.isSubscriber,
            skipInitialDefaults = skipInitialDefaults
        )
    }

    fun subscribe(

        subredditIds: List<String>? = null,
        subredditNames: List<String>? = null,

        action: Boolean,

        skipInitialDefaults: Boolean = true

    ): Any? {

        val authMap = getHeaderMap()
        val req = api.subscribe(
            subredditIds = subredditIds?.joinToString(separator = ","),
            subredditNames = subredditNames?.joinToString(separator = ","),
            action = if (action) "sub" else "unsub",
            skipInitialDefaults = if (action) skipInitialDefaults else null,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun fetchSubredditsSearch(

        query: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,
        sorting: SubredditSearchSorting = SubredditsSearchFetcher.DEFAULT_SORTING,

        disableLegacyEncoding: Boolean = false

    ): SubredditsSearchFetcher {

        return SubredditsSearchFetcher(
            api = api,
            query = query,
            limit = limit,
            sorting = sorting,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }
}
