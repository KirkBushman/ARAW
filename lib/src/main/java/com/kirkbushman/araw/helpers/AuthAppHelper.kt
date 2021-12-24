package com.kirkbushman.araw.helpers

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager

class AuthAppHelper(

    context: Context,

    clientId: String,
    redirectUrl: String,
    scopes: Array<String>,

    logging: Boolean = false,
    disableLegacyEncoding: Boolean = false

) : AuthHelper(
    logging = logging,
    disableLegacyEncoding = disableLegacyEncoding
) {

    override val auth by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setStorageManager(SharedPrefsStorageManager(context))
            .setApplicationCredentials(
                clientId = clientId,
                redirectUrl = redirectUrl
            )
            .setScopes(scopes)
            .setLogging(logging)
            .build()
    }

    private var returnUrl: String? = null

    fun startWebViewAuthentication(webView: WebView, onBearerFetched: (String) -> Unit) {

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                if (url != null && isRedirectedUrl(url)) {

                    onBearerFetched(url)
                }
            }
        }

        webView.clearFormData()
        webView.loadUrl(provideAuthUrl())
    }

    fun provideAuthUrl(): String {
        return auth.provideAuthorizeUrl()
    }

    fun isRedirectedUrl(url: String): Boolean {
        return auth.isRedirectedUrl(url)
    }

    fun getRedditClient(url: String): RedditClient? {

        returnUrl = url

        return getRedditClient()
    }

    override fun getRedditClient(): RedditClient? {

        val savedClient = getSavedRedditClient()
        if (savedClient != null) {
            return savedClient
        }

        if (returnUrl == null) {
            throw IllegalStateException("Redirected url is not been set! Have you called")
        }

        val bearer = auth.authenticate(returnUrl)
        if (bearer != null) {

            return RedditClient(
                bearer = bearer,
                disableLegacyEncoding = disableLegacyEncoding,
                logging = logging
            )
        }

        return null
    }
}
