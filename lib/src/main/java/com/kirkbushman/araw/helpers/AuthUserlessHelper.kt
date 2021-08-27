package com.kirkbushman.araw.helpers

import android.content.Context
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class AuthUserlessHelper(

    context: Context,

    clientId: String,
    deviceId: String? = null,

    logging: Boolean = false,
    disableLegacyEncoding: Boolean = false

) : AuthHelper(logging, disableLegacyEncoding) {

    override val auth by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setStorageManager(SharedPrefsStorageManager(context))
            .setUserlessCredentials(
                clientId = clientId,
                deviceId = deviceId
            )
            .setLogging(logging)
            .build()
    }

    override fun getRedditClient(): RedditClient? {

        val savedClient = getSavedRedditClient()
        if (savedClient != null) {
            return savedClient
        }

        val bearer = auth.authenticate()
        if (bearer != null) {
            return RedditClient(bearer, disableLegacyEncoding, logging)
        }

        return null
    }
}
