package com.kirkbushman.sampleapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.activity_api_detail.*

class ApiDetailActivity : AppCompatActivity() {

    companion object {

        private const val PARAM_API_CALL = "param_intent_api_call"

        private const val API_ME = "param_api_call_me"
        private const val API_MY_BLOCKED = "param_api_call_my_blocked"
        private const val API_MY_FRIENDS = "param_api_call_my_friends"
        private const val API_MY_KARMA = "param_api_call_my_karma"
        private const val API_MY_PREFS = "param_api_call_my_prefs"
        private const val API_MY_TROPHIES = "param_api_call_my_trophies"

        fun startApiMe(context: Context) {
            start(context, API_ME)
        }

        fun startApiMyBlocked(context: Context) {
            start(context, API_MY_BLOCKED)
        }

        fun startApiMyFriends(context: Context) {
            start(context, API_MY_FRIENDS)
        }

        fun startApiMyKarma(context: Context) {
            start(context, API_MY_KARMA)
        }

        fun startApiMyPrefs(context: Context) {
            start(context, API_MY_PREFS)
        }

        fun startApiMyTrophies(context: Context) {
            start(context, API_MY_TROPHIES)
        }

        fun start(context: Context, call: String) {

            val intent = Intent(context, ApiDetailActivity::class.java)
            intent.putExtra(PARAM_API_CALL, call)

            context.startActivity(intent)
        }
    }

    private val client by lazy { TestApplication.instance.getClient() }
    private val apiParam by lazy { intent.getStringExtra(PARAM_API_CALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_detail)

        var result = ""
        doAsync(doWork = { result = fetchCall() }, onPost = { api_detail.text = result })
    }

    private fun fetchCall(): String {

        when (apiParam) {
            API_ME -> {
                val me = client?.accountClient?.me()
                return me.toString()
            }

            API_MY_BLOCKED -> {
                val blocked = client?.accountClient?.myBlocked()
                return blocked.toString()
            }

            API_MY_FRIENDS -> {
                val friends = client?.accountClient?.myFriends()
                return friends.toString()
            }

            API_MY_KARMA -> {
                val karma = client?.accountClient?.myKarma()
                return karma.toString()
            }

            API_MY_PREFS -> {
                val prefs = client?.accountClient?.myPrefs()
                return prefs.toString()
            }

            API_MY_TROPHIES -> {
                val trophies = client?.accountClient?.myTrophies()
                return trophies.toString()
            }
        }

        return "Api call not registered!"
    }
}
