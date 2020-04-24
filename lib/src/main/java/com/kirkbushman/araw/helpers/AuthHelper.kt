package com.kirkbushman.araw.helpers

import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.models.TokenBearer

abstract class AuthHelper(protected val logging: Boolean) {

    protected abstract val auth: RedditAuth

    protected var bearer: TokenBearer? = null

    fun shouldLogin(): Boolean {
        return !hasSavedBearer()
    }

    fun hasSavedBearer(): Boolean {
        return auth.hasSavedBearer() &&
                // check the saved bearer is of the same type of the helper
                auth.getSavedBearerType() == auth.getAuthType()
    }

    fun getSavedBearer(): TokenBearer? {

        if (hasSavedBearer()) {
            return auth.getSavedBearer()
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
