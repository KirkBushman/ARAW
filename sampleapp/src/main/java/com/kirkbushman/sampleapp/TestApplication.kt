package com.kirkbushman.sampleapp

import android.app.Application
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.helpers.AuthAppHelper
import com.kirkbushman.araw.helpers.AuthUserlessHelper
import org.xmlpull.v1.XmlPullParser

class TestApplication : Application() {

    companion object {
        lateinit var instance: TestApplication
    }

    init {
        instance = this
    }

    fun getAuthHelper(): AuthAppHelper {

        val creds = loadCredentials()

        return AuthAppHelper(
            context = this,
            clientId = creds.clientId,
            redirectUrl = creds.redirectUrl,
            scopes = creds.scopes.toTypedArray(),
            logging = true
        )
    }

    fun getUserlessHelper(): AuthUserlessHelper {

        val creds = loadCredentials()

        return AuthUserlessHelper(
            context = this,
            clientId = creds.clientId,
            deviceId = null,
            logging = true
        )
    }

    private var client: RedditClient? = null
    fun getClient(): RedditClient? {
        return client
    }
    fun setClient(client: RedditClient) {
        this.client = client
    }

    private fun loadCredentials(): Credentials {

        val isTravis = System.getenv("TRAVIS") != null && System.getenv("TRAVIS")!!.toBoolean()
        return if (isTravis) {
            loadCredentialsFromTravis()
        } else {
            loadCredentialsFromXml()
        }
    }

    private fun loadCredentialsFromTravis(): Credentials {

        val clientId = System.getenv("clientId")
        val redirectUrl = System.getenv("redirectUrl")

        val scriptClientId = System.getenv("scriptClientId")
        val scriptClientSecret = System.getenv("scriptClientSecret")

        val username = System.getenv("username")
        val password = System.getenv("password")

        val scopes = ArrayList<String>()
        scopes.addAll(System.getenv("scopes")?.split(',') ?: emptyList())

        return Credentials(
            clientId = clientId!!,
            redirectUrl = redirectUrl!!,

            scriptClientId = scriptClientId!!,
            scriptClientSecret = scriptClientSecret!!,
            username = username!!,
            password = password!!,

            scopes
        )
    }

    private fun loadCredentialsFromXml(): Credentials {
        val xpp = resources.getXml(R.xml.credentials)

        var clientId = ""
        var redirectUrl = ""

        var scriptClientId = ""
        var scriptClientSecret = ""
        var username = ""
        var password = ""

        val scopes = ArrayList<String>()

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

            when (xpp.eventType) {

                XmlPullParser.START_TAG -> {

                    when (xpp.name) {
                        "clientId" -> clientId = xpp.nextText()
                        "redirectUrl" -> redirectUrl = xpp.nextText()
                        "scope" -> scopes.add(xpp.nextText())
                        "scriptClientId" -> scriptClientId = xpp.nextText()
                        "scriptClientSecret" -> scriptClientSecret = xpp.nextText()
                        "username" -> username = xpp.nextText()
                        "password" -> password = xpp.nextText()
                    }
                }
            }

            xpp.next()
        }

        return Credentials(
            clientId,
            redirectUrl,

            scriptClientId,
            scriptClientSecret,
            username,
            password,

            scopes
        )
    }
}
