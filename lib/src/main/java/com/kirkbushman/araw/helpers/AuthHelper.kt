package com.kirkbushman.araw.helpers

import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.models.bearers.TokenBearer

abstract class AuthHelper(protected val logging: Boolean) {

    protected abstract val auth: RedditAuth

    fun shouldLogin(): Boolean {
        return !auth.hasSavedBearer()
    }

    fun hasSavedBearer(): Boolean {
        return auth.hasSavedBearer()
    }

    fun getSavedBearer(): TokenBearer? {

        if (auth.hasSavedBearer()) {
            return auth.retrieveSavedBearer()
        }

        return null
    }

    fun getSavedRedditClient(): RedditClient? {

        if (auth.hasSavedBearer()) {

            val bearer = getSavedBearer()
            if (bearer != null) {

                return RedditClient(bearer, logging)
            }
        }

        return null
    }

    abstract fun getRedditClient(): RedditClient?

    fun forceRenew() {

        if (hasSavedBearer()) {
            val bearer = getSavedBearer()
            bearer?.renewToken()
        }
    }

    fun forceRevoke() {

        if (hasSavedBearer()) {
            val bearer = getSavedBearer()
            bearer?.revokeToken()
        }
    }
}
