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
import com.kirkbushman.auth.models.bearers.TokenBearer
import com.kirkbushman.auth.models.enums.AuthType
import retrofit2.Retrofit

class RedditClient @JvmOverloads constructor(

    private val bearer: TokenBearer,
    private val disableLegacyEncoding: Boolean = false,
    logging: Boolean = false

) {

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

    val accountsClient by lazy { AccountsClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val contributionsClient by lazy { ContributionsClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val messagesClient by lazy { MessagesClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val multisClient by lazy { MultisClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val subredditsClient by lazy { SubredditsClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val searchClient by lazy { SearchClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val redditorsClient by lazy { RedditorsClient(api, disableLegacyEncoding, ::getHeaderMap) }
    val wikisClient by lazy { WikisClient(api, disableLegacyEncoding, ::getHeaderMap) }

    fun getCurrentUser(): Me? {
        return accountsClient.getCurrentUser()
    }

    private fun getHeaderMap(): Map<String, String> {
        if (bearer.getAuthType() == AuthType.NONE)
            return emptyMap()
        return mapOf("Authorization" to "bearer ".plus(bearer.getAccessToken()))
    }
}
