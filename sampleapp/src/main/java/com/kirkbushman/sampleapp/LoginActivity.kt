package com.kirkbushman.sampleapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.util.DoAsync
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {

        private const val PARAM_AUTO_LOGIN = "intent_param_auto_login"

        fun start(context: Context, stopAutoLogin: Boolean = false) {

            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(PARAM_AUTO_LOGIN, stopAutoLogin)

            context.startActivity(intent)
        }
    }

    private val stopAutoLogin by lazy { intent.getBooleanExtra(PARAM_AUTO_LOGIN, false) }

    private val app = TestApplication.instance
    private val appAuth = app.getAuthHelper()
    private val userlessAuth = app.getUserlessHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        checkAuthStatus()

        if (!stopAutoLogin) {

            if (!appAuth.shouldLogin() || !userlessAuth.shouldLogin()) {

                if (!appAuth.shouldLogin()) {

                    savedInstalledApp()
                }

                if (!userlessAuth.shouldLogin()) {

                    savedUserless()
                }
            }
        }

        bttn_app_login.setOnClickListener {

            browser.visibility = View.VISIBLE
            text_current_login.visibility = View.GONE
            bttn_app_login.visibility = View.GONE
            bttn_userless_login.visibility = View.GONE

            if (!appAuth.shouldLogin()) {

                savedInstalledApp()
            } else {

                fetchInstalledApp()
            }
        }

        bttn_userless_login.setOnClickListener {

            if (!userlessAuth.shouldLogin()) {

                savedUserless()
            } else {

                fetchUserless()
            }
        }
    }

    private fun savedInstalledApp() {

        DoAsync(
            doWork = {

                val client = appAuth.getSavedRedditClient()
                if (client != null) {
                    app.setClient(client)
                }
            },
            onPost = {

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        )
    }

    private fun savedUserless() {

        DoAsync(
            doWork = {

                val client = userlessAuth.getSavedRedditClient()
                if (client != null) {
                    app.setClient(client)
                }
            },
            onPost = {

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        )
    }

    private fun fetchInstalledApp() {

        appAuth.startWebViewAuthentication(browser) {

            browser.stopLoading()

            DoAsync(
                doWork = {

                    if (!userlessAuth.shouldLogin()) {
                        userlessAuth.forceRevoke()
                    }

                    appAuth.retrieveTokenBearerFromUrl(it)

                    val client = appAuth.getRedditClient()
                    if (client != null) {
                        TestApplication.instance.setClient(client)
                    }

                    checkAuthStatus()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }

    private fun fetchUserless() {

        var client: RedditClient? = null

        DoAsync(
            doWork = {

                if (!appAuth.shouldLogin()) {
                    appAuth.forceRevoke()
                }

                client = userlessAuth.getRedditClient()
            },
            onPost = {

                if (client != null) {
                    TestApplication.instance.setClient(client!!)
                }

                checkAuthStatus()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        )
    }

    private fun checkAuthStatus() {

        if (!appAuth.shouldLogin()) {

            text_current_login.text = getString(R.string.header_login_type, "INSTALLED_APP")
        } else if (!userlessAuth.shouldLogin()) {

            text_current_login.text = getString(R.string.header_login_type, "USERLESS")
        } else {
            text_current_login.text = getString(R.string.header_login_no_logged_in)
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
