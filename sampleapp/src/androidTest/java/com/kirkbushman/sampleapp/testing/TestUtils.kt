package com.kirkbushman.sampleapp.testing

import android.content.Context
import com.kirkbushman.sampleapp.R
import org.xmlpull.v1.XmlPullParser

object TestUtils {

    fun loadCredentialsFromXml(context: Context): Credentials {

        val xpp = context.resources.getXml(R.xml.credentials)

        var clientId = ""
        var redirectUrl = ""
        val scopes = ArrayList<String>()
        var username = ""
        var password = ""

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

            when (xpp.eventType) {

                XmlPullParser.START_TAG -> {

                    when (xpp.name) {
                        "clientId" -> clientId = xpp.nextText()
                        "redirectUrl" -> redirectUrl = xpp.nextText()
                        "scope" -> scopes.add(xpp.nextText())
                        "username" -> username = xpp.nextText()
                        "password" -> password = xpp.nextText()
                    }
                }
            }

            xpp.next()
        }

        return Credentials(clientId, redirectUrl, scopes, username, password)
    }
}
