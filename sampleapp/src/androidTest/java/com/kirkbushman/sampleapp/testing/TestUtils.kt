package com.kirkbushman.sampleapp.testing

import android.content.Context
import com.kirkbushman.auth.RedditAuth
import com.kirkbushman.auth.managers.SharedPrefsStorageManager
import com.kirkbushman.auth.models.TokenBearer
import com.kirkbushman.sampleapp.R
import org.xmlpull.v1.XmlPullParser

object TestUtils {

    fun loadCredentialsFromXml(context: Context): TestCredentials {

        val xpp = context.resources.getXml(R.xml.credentials)

        var clientId = ""
        var clientSecret = ""

        val scopes = ArrayList<String>()

        var username = ""
        var password = ""

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

            when (xpp.eventType) {

                XmlPullParser.START_TAG -> {

                    when (xpp.name) {
                        "clientId" -> clientId = xpp.nextText()
                        "clientSecret" -> clientSecret = xpp.nextText()
                        "scope" -> scopes.add(xpp.nextText())
                        "username" -> username = xpp.nextText()
                        "password" -> password = xpp.nextText()

                        else -> {}
                    }
                }
            }

            xpp.next()
        }

        return TestCredentials(clientId, clientSecret, scopes, username, password)
    }

    fun getAuthManager(context: Context): RedditAuth {
        val creds = loadCredentialsFromXml(context)

        return RedditAuth.Builder()
            .setCredentials(creds.username, creds.password, creds.clientId, creds.clientSecret)
            .setScopes(creds.scopes.toTypedArray())
            .setStorageManager(SharedPrefsStorageManager(context))
            .build()
    }

    fun getTokenBearer(auth: RedditAuth): TokenBearer {
        return auth.getTokenBearer() ?: throw IllegalStateException("Bearer is null!")
    }
}
