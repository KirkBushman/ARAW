package com.kirkbushman.sampleapp.testing.integrationTesting

import android.content.Intent
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.clearElement
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.DriverAtoms.webClick
import androidx.test.espresso.web.webdriver.DriverAtoms.webKeys
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kirkbushman.sampleapp.LoginActivity
import com.kirkbushman.sampleapp.testing.TestUtils
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<LoginActivity> =
        object : ActivityTestRule<LoginActivity>(LoginActivity::class.java, false, true) {

            override fun afterActivityLaunched() {
                // Technically we do not need to do this - WebViewActivity has javascript turned on.
                // Other WebViews in your app may have javascript turned off, however since the only way
                // to automate WebViews is through javascript, it must be enabled.
                onWebView().forceJavascriptEnabled()
            }
        }

    @Test
    fun testLogin() {

        mActivityRule.launchActivity(Intent())

        val creds = TestUtils.loadCredentialsFromXml(mActivityRule.activity)

        onWebView()
                // set username text
            .withElement(findElement(Locator.ID, "username"))
            .perform(clearElement())
            .perform(webKeys(creds.username))
                // set password text
            .withElement(findElement(Locator.ID, "password"))
            .perform(clearElement())
            .perform(webKeys(creds.password))
                // click submit
            .withElement(findElement(Locator.ID, "submit"))
            .perform(webClick())
    }
}
