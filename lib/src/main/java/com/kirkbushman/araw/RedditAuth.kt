package com.kirkbushman.araw

import android.content.Context
import com.kirkbushman.auth.managers.RedditAuthManager
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class RedditAuth(context: Context, clientId: String, redirectUrl: String, scopes: Array<String>) {

    private val authManager by lazy {

        RedditAuthManager.Builder()
            .setClientId(clientId)
            .setRedirectUrl(redirectUrl)
            .setScopes(scopes)
            .setStorageManager(SharedPrefsStorageManager(context))
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