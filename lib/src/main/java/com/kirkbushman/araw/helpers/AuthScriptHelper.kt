package com.kirkbushman.araw.helpers

import android.content.Context
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class AuthScriptHelper(

    context: Context,

    clientId: String,
    clientSecret: String,
    username: String,
    password: String,

    logging: Boolean = false
) : AuthHelper(logging) {

    override val auth by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setStorageManager(SharedPrefsStorageManager(context))
            .setScriptAuthCredentials(
                clientId = clientId,
                clientSecret = clientSecret,
                username = username,
                password = password
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
            return RedditClient(bearer, logging)
        }

        return null
    }
}
