package com.kirkbushman.sampleapp.instrumented.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.instrumented.TestUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountClientTests {

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var client: RedditClient? = null

    @Before
    fun onPre() {
        client = RedditClient(bearer, true, true)
    }

    @Test
    fun testSelfAccountMe() {

        var exception: Exception? = null

        try {

            val me = client?.accountsClient?.me()
            Assert.assertNotEquals("Assert that the Object is not null", null, me)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun testSelfAccountFriends() {

        var exception: Exception? = null

        try {

            val friends = client?.accountsClient?.myFriends()
            Assert.assertNotEquals("Assert that the Object is not null", null, friends)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun testSelfAccountKarma() {

        var exception: Exception? = null

        try {

            val karma = client?.accountsClient?.myKarma()
            Assert.assertNotEquals("Assert that the Object is not null", null, karma)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun testSelfAccountPrefs() {

        var exception: Exception? = null

        try {

            val prefs = client?.accountsClient?.myPrefs()
            Assert.assertNotEquals("Assert that the Object is not null", null, prefs)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun testSelfAccountTrophies() {

        var exception: Exception? = null

        try {

            val trophies = client?.accountsClient?.myTrophies()
            Assert.assertNotEquals("Assert that the Object is not null", null, trophies)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            Assert.assertNull("Check there are no exceptions thrown", exception)
        }
    }
}
