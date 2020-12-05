package com.kirkbushman.sampleapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.databinding.ActivityLoginBinding
import com.kirkbushman.sampleapp.util.DoAsync

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

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
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

        binding.bttnAppLogin.setOnClickListener {

            binding.browser.visibility = View.VISIBLE
            binding.textCurrentLogin.visibility = View.GONE
            binding.bttnAppLogin.visibility = View.GONE
            binding.bttnUserlessLogin.visibility = View.GONE

            if (!appAuth.shouldLogin()) {

                savedInstalledApp()
            } else {

                fetchInstalledApp()
            }
        }

        binding.bttnUserlessLogin.setOnClickListener {

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

        appAuth.startWebViewAuthentication(binding.browser) {

            binding.browser.stopLoading()

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

            binding.textCurrentLogin.text = getString(R.string.header_login_type, "INSTALLED_APP")
        } else if (!userlessAuth.shouldLogin()) {

            binding.textCurrentLogin.text = getString(R.string.header_login_type, "USERLESS")
        } else {
            binding.textCurrentLogin.text = getString(R.string.header_login_no_logged_in)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding.browser.removeAllViews()
        binding.browser.destroy()
    }
}
