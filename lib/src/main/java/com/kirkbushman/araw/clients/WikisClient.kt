package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.exceptions.WikiDisabledException
import com.kirkbushman.araw.models.WikiPage
import com.kirkbushman.araw.models.WikiRevision
import com.kirkbushman.araw.models.mixins.SubredditData

class WikisClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: SubredditData, disableLegacyEncoding: Boolean = false): WikiPage? {
        return wiki(subreddit.displayName, disableLegacyEncoding)
    }

    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: String, disableLegacyEncoding: Boolean = false): WikiPage? {

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

    fun wikiPage(subreddit: SubredditData, page: String, disableLegacyEncoding: Boolean = false): WikiPage? {
        return wikiPage(subreddit.displayName, page, disableLegacyEncoding)
    }

    fun wikiPage(subreddit: String, page: String, disableLegacyEncoding: Boolean = false): WikiPage? {

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

    fun wikiPages(subreddit: SubredditData, disableLegacyEncoding: Boolean = false): List<String>? {
        return wikiPages(subreddit.displayName, disableLegacyEncoding)
    }

    fun wikiPages(subreddit: String, disableLegacyEncoding: Boolean = false): List<String>? {

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

    fun wikiRevision(subreddit: SubredditData, page: String, disableLegacyEncoding: Boolean = false): List<WikiRevision>? {
        return wikiRevision(subreddit.displayName, page, disableLegacyEncoding)
    }

    fun wikiRevision(subreddit: String, page: String, disableLegacyEncoding: Boolean = false): List<WikiRevision>? {

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

    fun wikiRevisions(subreddit: SubredditData, disableLegacyEncoding: Boolean = false): List<WikiRevision>? {
        return wikiRevisions(subreddit.displayName, disableLegacyEncoding)
    }

    fun wikiRevisions(subreddit: String, disableLegacyEncoding: Boolean = false): List<WikiRevision>? {

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
