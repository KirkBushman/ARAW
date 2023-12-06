package com.kirkbushman.araw.helpers

import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.RedditClient.Companion.getRetrofit
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.StorageManager
import com.kirkbushman.auth.models.Token
import com.kirkbushman.auth.models.bearers.TokenBearer
import com.kirkbushman.auth.models.creds.UserlessCredentials
import com.kirkbushman.auth.models.enums.AuthType
import com.kirkbushman.auth.utils.Utils.buildDefaultClient

class NoAuthHelper(logging: Boolean, disableLegacyEncoding: Boolean) :
    AuthHelper(logging, disableLegacyEncoding) {
    private val tokenBearer: TokenBearer
    private val storageManager: StorageManager

    init {
        storageManager = object : StorageManager {
            override fun isAuthed(): Boolean = true
            override fun authType(): AuthType = AuthType.NONE
            override fun hasToken(): Boolean = true
            override fun getToken(): Token? = null
            override fun clearAll() = Unit
            override fun saveToken(token: Token, authType: AuthType): Unit =
                throw IllegalStateException("NoAuth doesn't use tokens")
        }
        tokenBearer = object : TokenBearer(storageManager) {
            override fun renewToken(token: Token): Token? = token
            override fun revokeToken(token: Token): Boolean = false
        }
    }

    override val auth: RedditAuth
        get() {
            val client = buildDefaultClient(getRetrofit(logging), logging)
            val cred = UserlessCredentials("", "DO_NOT_TRACK_THIS_DEVICE")
            return object : RedditAuth(client, cred, storageManager, storageManager.authType()) {
                override fun renewToken(token: Token): Token? = tokenBearer.renewToken(token)
                override fun revokeToken(token: Token): Boolean = tokenBearer.revokeToken(token)
                override fun retrieveSavedBearer(): TokenBearer? = tokenBearer
                override fun fetchToken(): Token? = tokenBearer.getToken()
            }
        }

    override fun getRedditClient(): RedditClient? =
        RedditClient(tokenBearer, disableLegacyEncoding, logging)
}
