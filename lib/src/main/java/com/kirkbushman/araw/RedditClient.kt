package com.kirkbushman.araw

import com.kirkbushman.araw.clients.AccountsClient
import com.kirkbushman.araw.clients.ContributionsClient
import com.kirkbushman.araw.clients.MessagesClient
import com.kirkbushman.araw.clients.MultisClient
import com.kirkbushman.araw.clients.SubredditsClient
import com.kirkbushman.araw.clients.RedditorsClient
import com.kirkbushman.araw.clients.SearchClient
import com.kirkbushman.araw.clients.WikisClient
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.utils.Utils.buildRetrofit
import com.kirkbushman.auth.models.TokenBearer
import retrofit2.Retrofit

class RedditClient @JvmOverloads constructor (private val bearer: TokenBearer, logging: Boolean = false) {

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

    private val api by lazy { getApi(logging) }

    val accountsClient by lazy { AccountsClient(api, ::getHeaderMap) }
    val contributionsClient by lazy { ContributionsClient(api, ::getHeaderMap) }
    val messagesClient by lazy { MessagesClient(api, ::getHeaderMap) }
    val multisClient by lazy { MultisClient(api, ::getHeaderMap) }
    val subredditsClient by lazy { SubredditsClient(api, ::getHeaderMap) }
    val searchClient by lazy { SearchClient(api, ::getHeaderMap) }
    val redditorsClient by lazy { RedditorsClient(api, ::getHeaderMap) }
    val wikisClient by lazy { WikisClient(api, ::getHeaderMap) }

    fun getCurrentUser(): Me? {
        return accountsClient.getCurrentUser()
    }

    private fun getHeaderMap(): HashMap<String, String> {
        return hashMapOf("Authorization" to "bearer ".plus(bearer.getAccessToken()))
    }
}
