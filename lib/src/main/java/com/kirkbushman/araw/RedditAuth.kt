package com.kirkbushman.araw

import android.content.Context
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class RedditAuth(context: Context, clientId: String, redirectUrl: String, scopes: Array<String>, logging: Boolean = false) {

    private val authManager by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setCredentials(clientId, redirectUrl)
            .setScopes(scopes)
            .setStorageManager(SharedPrefsStorageManager(context))
            .setLogging(logging)
            .build()
    }

    fun shouldLogin(): Boolean {
        return !authManager.hasSavedBearer()
    }

    fun provideAuthUrl(): String {
        return authManager.provideAuthorizeUrl()
    }

    fun isRedirectedUrl(url: String): Boolean {
        return authManager.isRedirectedUrl(url)
    }

    fun getSavedRedditClient(logging: Boolean): RedditClient {
        return RedditClient(authManager.getSavedBearer(), logging)
    }

    fun getRedditClient(url: String, logging: Boolean): RedditClient {
        val bearer = authManager.getTokenBearer(url) ?: throw IllegalStateException("Bearer is null!")
        return RedditClient(bearer, logging)
    }
}
