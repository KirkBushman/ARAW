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

    logging: Boolean = false
) : AuthHelper(logging) {

    override val auth by lazy {

        RedditAuth.Builder()
            .setRetrofit(RedditClient.getRetrofit(logging))
            .setApplicationCredentials(clientId, redirectUrl)
            .setScopes(scopes)
            .setStorageManager(SharedPrefsStorageManager(context))
            .setLogging(logging)
            .build()
    }

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

    fun retrieveTokenBearerFromUrl(url: String) {
        bearer = auth.getTokenBearer(url)
    }

    override fun getRedditClient(): RedditClient? {

        if (bearer != null) {

            return RedditClient(bearer!!, logging)
        }

        if (hasSavedBearer()) {

            val client = getSavedRedditClient()
            if (client != null) {

                return client
            }
        }

        return null
    }
}
