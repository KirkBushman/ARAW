package com.kirkbushman.araw.helpers

import android.content.Context
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class AuthUserlessHelper(

    context: Context,

    clientId: String,
    deviceId: String? = null,

    scopes: Array<String>,

    logging: Boolean = false
) : AuthHelper(logging) {

    override val auth by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setUserlessCredentials(clientId, deviceId)
            .setScopes(scopes)
            .setStorageManager(SharedPrefsStorageManager(context))
            .setLogging(logging)
            .build()
    }

    override fun getRedditClient(): RedditClient? {

        bearer = auth.getTokenBearer()
        return when {
            bearer != null ->
                RedditClient(bearer!!, logging)
            hasSavedBearer() ->
                getSavedRedditClient()

            else -> null
        }
    }
}
