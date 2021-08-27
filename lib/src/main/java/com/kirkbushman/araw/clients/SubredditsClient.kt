package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.models.Flair
import com.kirkbushman.araw.models.responses.SubmitResponse
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.SubredditRule
import com.kirkbushman.araw.models.User
import com.kirkbushman.araw.models.commons.SubmissionKind
import com.kirkbushman.araw.models.enums.SubmissionsSorting
import com.kirkbushman.araw.models.enums.TimePeriod
import com.kirkbushman.araw.models.base.SubredditData
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.HashMap

class SubredditsClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> HashMap<String, String>

) {

    @WorkerThread
    fun subreddit(subreddit: String): SubredditData? {

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

    @WorkerThread
    fun subreddits(vararg ids: String): List<SubredditData>? {

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

    @WorkerThread
    fun subredditBanned(subreddit: SubredditData): List<User>? {
        return subredditBanned(subreddit.fullname)
    }

    @WorkerThread
    fun subredditBanned(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "banned",
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
    fun subredditMuted(subreddit: SubredditData): List<User>? {
        return subredditMuted(subreddit.fullname)
    }

    @WorkerThread
    fun subredditMuted(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "muted",
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
    fun subredditWikiBanned(subreddit: SubredditData): List<User>? {
        return subredditWikiBanned(subreddit.fullname)
    }

    @WorkerThread
    fun subredditWikiBanned(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "wikibanned",
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
    fun subredditContributors(subreddit: SubredditData): List<User>? {
        return subredditContributors(subreddit.fullname)
    }

    @WorkerThread
    fun subredditContributors(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "contributors",
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
    fun subredditWikiContributors(subreddit: SubredditData): List<User>? {
        return subredditWikiContributors(subreddit.fullname)
    }

    @WorkerThread
    fun subredditWikiContributors(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "wikicontributors",
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
    fun subredditModerators(subreddit: SubredditData): List<User>? {
        return subredditModerators(subreddit.fullname)
    }

    @WorkerThread
    fun subredditModerators(subredditName: String): List<User>? {

        val authMap = getHeaderMap()
        val req = api.subredditInfo(
            subreddit = subredditName,
            where = "moderators",
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
    fun rules(subreddit: SubredditData): Array<SubredditRule>? {
        return rules(subreddit.displayName)
    }

    @WorkerThread
    fun rules(subreddit: String): Array<SubredditRule>? {

        val authMap = getHeaderMap()
        val req = api.rules(
            subreddit = subreddit,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.rules
    }

    @WorkerThread
    fun subredditFlairs(subreddit: SubredditData): List<Flair>? {

        return subredditFlairs(subreddit.displayName)
    }

    @WorkerThread
    fun subredditFlairs(subreddit: String): List<Flair>? {

        val authMap = getHeaderMap()
        val req = api.subredditFlairs(
            subreddit = subreddit,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun frontpage(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

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

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

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

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

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

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

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

    @WorkerThread
    fun submit(

        subreddit: SubredditData,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean? = null,
        extension: String? = null,

        sendReplies: Boolean = false,
        submitType: String? = null,
        isNsfw: Boolean? = null,
        isSpoiler: Boolean? = null,
        isOriginalContent: Boolean? = null,

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

            sendReplies = sendReplies,
            submitType = submitType,
            isNsfw = isNsfw,
            isSpoiler = isSpoiler,
            isOriginalContent = isOriginalContent,

            validateOnSubmit = validateOnSubmit,
            showErrorList = showErrorList
        )
    }

    @WorkerThread
    fun submit(

        subredditName: String,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean? = null,
        extension: String? = null,

        sendReplies: Boolean = false,
        submitType: String? = null,
        isNsfw: Boolean? = null,
        isSpoiler: Boolean? = null,
        isOriginalContent: Boolean? = null,

        validateOnSubmit: Boolean? = null,
        showErrorList: Boolean? = null

    ): SubmitResponse? {

        val authMap = getHeaderMap()
        val req = api.submit(
            subreddit = subredditName,

            title = title,
            kind = kind.toString().lowercase(),

            text = (if (kind == SubmissionKind.SELF) text else null),
            url = (if (kind != SubmissionKind.SELF) url else null),

            resubmit = resubmit,
            extension = extension,
            rawJson = (if (disableLegacyEncoding) 1 else null),

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

    @WorkerThread
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

    @WorkerThread
    fun subscribe(

        subredditName: String,
        action: Boolean,
        skipInitialDefaults: Boolean = true

    ): Any? {

        return subscribe(

            subredditNames = listOf(subredditName),

            action = action,
            skipInitialDefaults = skipInitialDefaults
        )
    }

    @WorkerThread
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
}
