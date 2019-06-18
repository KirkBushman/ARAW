package com.kirkbushman.sampleapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {

        private const val LOGGING = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val auth = TestApplication.instance.auth
        if (!auth.shouldLogin()) {

            val client = auth.getSavedRedditClient(LOGGING)
            TestApplication.instance.setClient(client)

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        } else {

            CookieManager.getInstance().removeAllCookies(null)

            browser.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

                    if (auth.isRedirectedUrl(url)) {
                        browser.stopLoading()

                        doAsync(doWork = {

                            val client = auth.getRedditClient(url, LOGGING)
                            TestApplication.instance.setClient(client)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        })
                    }
                }
            }

            browser.clearFormData()
            browser.loadUrl(auth.provideAuthUrl())
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (browser != null) {
            browser.removeAllViews()
            browser.destroy()
        }
    }
}