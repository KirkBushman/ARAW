package com.kirkbushman.araw.test.integrationTesting

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.R
import com.kirkbushman.araw.RedditAuth
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.test.TestUtils
import com.kirkbushman.araw.test.doAsync
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {

        private const val LOGGING = true
    }

    private val auth by lazy {

        val creds = TestUtils.loadCredentialsFromXml(this)

        RedditAuth(this, creds.clientId, creds.redirectUrl, creds.scopes.toTypedArray())
    }

    private var client: RedditClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        if (!auth.shouldLogin()) {

            doAsync(doWork = {

                val client = auth.getSavedRedditClient(LOGGING)
                setClient(client)
            }, onPost = {

                Log.i("Test LoginActivity", "Login done!")
            })
        } else {

            CookieManager.getInstance().removeAllCookies(null)

            browser.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

                    if (auth.isRedirectedUrl(url)) {
                        browser.stopLoading()

                        doAsync(doWork = {

                            val client = auth.getRedditClient(url, LOGGING)
                            setClient(client)

                            Log.i("Test LoginActivity", "Login done!")
                        })
                    }
                }
            }

            browser.clearFormData()
            browser.loadUrl(auth.provideAuthUrl())
        }
    }

    fun getClient(): RedditClient? {
        return client
    }

    fun setClient(client: RedditClient) {
        this.client = client
    }
}