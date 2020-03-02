package com.kirkbushman.sampleapp.testing.integrationTesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Me
import com.kirkbushman.sampleapp.testing.TestUtils
import com.kirkbushman.sampleapp.testing.doAsync
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClientInitTest {

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var me: Me? = null

    private var firstClient: RedditClient? = null
    private var secondClient: RedditClient? = null
    private var thirdClient: RedditClient? = null

    @Before
    fun initClientTesting() {

        firstClient = RedditClient(bearer, true)
        me = firstClient!!.getCurrentUser()

        secondClient = RedditClient(bearer, true, me = me)

        thirdClient = RedditClient(bearer, true, fetchMe = { client ->

            doAsync(doWork = {
                client.me()
            })
        })

        Thread.sleep(5000)
    }

    @Test
    fun testClients() {

        assertTrue("The current user in the first and second Client should be the same", firstClient!!.getCurrentUser() == secondClient!!.getCurrentUser())
        assertTrue("The current user in the first and third Client should be the same", firstClient!!.getCurrentUser() == thirdClient!!.getCurrentUser())
        assertTrue("The current user in the second and third Client should be the same", secondClient!!.getCurrentUser() == thirdClient!!.getCurrentUser())
    }
}
