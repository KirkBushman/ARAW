package com.kirkbushman.araw.clients

import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.exceptions.WikiDisabledException
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.models.WikiRevision
import com.kirkbushman.araw.models.base.SubredditData

class WikisClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> Map<String, String>

) {

    @WorkerThread
    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: SubredditData): WikiPage? {
        return wiki(subreddit.displayName)
    }

    @WorkerThread
    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: String): WikiPage? {

        val authMap = getHeaderMap()
        val req = api.wiki(
            subreddit = subreddit,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {

            val errorBody = res.errorBody()
            if (errorBody != null && errorBody.string().contains("WIKI_DISABLED")) {

                throw WikiDisabledException()
            }

            return null
        }

        return res.body()?.data
    }

    @WorkerThread
    fun wikiPage(subreddit: SubredditData, page: String): WikiPage? {
        return wikiPage(subreddit.displayName, page)
    }

    @WorkerThread
    fun wikiPage(subreddit: String, page: String): WikiPage? {

        val authMap = getHeaderMap()
        val req = api.wikiPage(
            subreddit = subreddit,
            page = page,
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
    fun wikiPages(subreddit: SubredditData): List<String>? {
        return wikiPages(subreddit.displayName)
    }

    @WorkerThread
    fun wikiPages(subreddit: String): List<String>? {

        val authMap = getHeaderMap()
        val req = api.wikiPages(
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
    fun wikiRevision(

        subreddit: SubredditData,
        page: String

    ): List<WikiRevision>? {
        return wikiRevision(subreddit.displayName, page)
    }

    @WorkerThread
    fun wikiRevision(

        subreddit: String,
        page: String

    ): List<WikiRevision>? {

        val authMap = getHeaderMap()
        val req = api.wikiRevision(
            subreddit = subreddit,
            page = page,
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
    fun wikiRevisions(subreddit: SubredditData): List<WikiRevision>? {
        return wikiRevisions(subreddit.displayName)
    }

    @WorkerThread
    fun wikiRevisions(subreddit: String): List<WikiRevision>? {

        val authMap = getHeaderMap()
        val req = api.wikiRevisions(
            subreddit = subreddit,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children
    }
}
