package com.kirkbushman.araw

import com.kirkbushman.araw.clients.AccountsClient
import com.kirkbushman.araw.clients.ContributionsClient
import com.kirkbushman.araw.clients.MessagesClient
import com.kirkbushman.araw.clients.SubredditsClient
import com.kirkbushman.araw.clients.RedditorsClient
import com.kirkbushman.araw.clients.WikisClient
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.utils.Utils.buildRetrofit
import com.kirkbushman.auth.models.TokenBearer
import retrofit2.Retrofit

class RedditClient(private val bearer: TokenBearer, logging: Boolean) {

    companion object {

        @Volatile
        private var retrofit: Retrofit? = null
        @Volatile
        private var api: RedditApi? = null

        @Synchronized
        fun getRetrofit(logging: Boolean = false): Retrofit {
            return synchronized(this) {

                if (retrofit == null) {
                    retrofit = buildRetrofit(logging)
                }

                retrofit!!
            }
        }

        @Synchronized
        fun getApi(logging: Boolean = false): RedditApi {
            return synchronized(this) {

                if (api == null) {
                    api = getRetrofit(logging).create(RedditApi::class.java)
                }

                api!!
            }
        }
    }

    private val api = getApi(logging)

    val accountsClient by lazy { AccountsClient(api, ::getCurrentUser, { me -> currentUser = me }, ::getHeaderMap) }
    val contributionsClient by lazy { ContributionsClient(api, ::getHeaderMap) }
    val messagesClient by lazy { MessagesClient(api, ::getHeaderMap) }
    val subredditsClient by lazy { SubredditsClient(api, ::getHeaderMap) }
    val redditorsClient by lazy { RedditorsClient(api, ::getHeaderMap) }
    val wikisClient by lazy { WikisClient(api, ::getHeaderMap) }

    private var currentUser: Me? = null
    fun getCurrentUser(): Me? = currentUser

    init {
        currentUser = accountsClient.me() ?: throw IllegalStateException("Could not found logged redditor")
    }

    fun searchSubreddits(
        query: String,
        exact: Boolean? = null,
        includeOver18: Boolean? = null,
        includeUnadvertisable: Boolean? = null
    ): SubredditSearchResult? {

        val authMap = getHeaderMap()
        val req = api.searchSubreddits(
            query = query,
            exact = exact,
            includeOver18 = includeOver18,
            includeUnadvertisable = includeUnadvertisable,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getRawAccessToken()))
    }
}
