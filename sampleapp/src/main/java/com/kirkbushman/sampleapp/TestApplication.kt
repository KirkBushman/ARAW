package com.kirkbushman.sampleapp

import android.app.Application
import com.kirkbushman.araw.RedditAuth
import com.kirkbushman.araw.RedditClient
import org.xmlpull.v1.XmlPullParser

class TestApplication : Application() {

    companion object {
        lateinit var instance: TestApplication
    }

    val auth by lazy {

        val creds = loadCredsFromFile()

        RedditAuth(this, creds.clientId, creds.redirectUrl, creds.scopes.toTypedArray())
    }

    init {
        instance = this
    }

    private var client: RedditClient? = null
    fun getClient(): RedditClient? {
        return client
    }
    fun setClient(client: RedditClient) {
        this.client = client
    }

    private fun loadCredsFromFile(): Credentials {
        val xpp = resources.getXml(R.xml.credentials)

        var clientId = ""
        var redirectUrl = ""
        val scopes = ArrayList<String>()

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

            when (xpp.eventType) {

                XmlPullParser.START_TAG -> {

                    when (xpp.name) {
                        "clientId" -> clientId = xpp.nextText()
                        "redirectUrl" -> redirectUrl = xpp.nextText()
                        "scope" -> scopes.add(xpp.nextText())
                    }
                }
            }

            xpp.next()
        }

        return Credentials(clientId, redirectUrl, scopes)
    }
}