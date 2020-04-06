package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.exceptions.WikiDisabledException
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.WikiPage

class WikisClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: Subreddit): WikiPage? {
        return wiki(subreddit.displayName)
    }

    @Throws(WikiDisabledException::class)
    fun wiki(subreddit: String): WikiPage? {

        val authMap = getHeaderMap()
        val req = api.wiki(subreddit = subreddit, header = authMap)
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

    fun wikiPage(subreddit: Subreddit, page: String): WikiPage? {
        return wikiPage(subreddit.displayName, page)
    }

    fun wikiPage(subreddit: String, page: String): WikiPage? {

        val authMap = getHeaderMap()
        val req = api.wikiPage(subreddit = subreddit, page = page, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun wikiPages(subreddit: Subreddit): List<String>? {
        return wikiPages(subreddit.displayName)
    }

    fun wikiPages(subreddit: String): List<String>? {

        val authMap = getHeaderMap()
        val req = api.wikiPages(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    fun wikiRevisions(subreddit: Subreddit): Any? {
        return wikiRevisions(subreddit.displayName)
    }

    fun wikiRevisions(subreddit: String): Any? {

        val authMap = getHeaderMap()
        val req = api.wikiRevisions(subreddit = subreddit, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }
}
