package com.kirkbushman.sampleapp.testing.unitTesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.testing.TestUtils
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModelsFetchTest {

    companion object {

        // very different subreddits
        private val subreddits = arrayOf("pics", "tifu", "bestof", "videos", "wtf")

        private const val LIMIT = 50
    }

    private val context by lazy { InstrumentationRegistry.getInstrumentation().targetContext.applicationContext }
    private val manager by lazy { TestUtils.getAuthManager(context) }
    private val bearer by lazy { TestUtils.getTokenBearer(manager) }

    private var client: RedditClient? = null

    @Before
    fun onPre() {
        client = RedditClient(bearer, true)
    }

    @Test
    fun modelMeTest() {
        val me = client?.accountsClient?.me()
        assertNotEquals("Assert that Me Object is not null", null, me)
    }

    @Test
    fun modelsSubmissionsTest() {

        subreddits.forEach {

            val fetcher = client?.contributionsClient?.submissions(it, limit = LIMIT)
            val submissions = fetcher?.fetchNext()
            assertNotEquals("Assert that submissions from /r/$it are not null", null, submissions)
            assertTrue("Assert that submissions from /r/$it are not empty", submissions?.isNotEmpty() ?: false)
        }
    }

    @Test
    fun modelsSubredditTest() {

        subreddits.forEach {

            val subreddit = client?.subredditsClient?.subreddit(it)
            assertNotEquals("Assert that subreddit models from /r/$it are not null", null, subreddit)
        }
    }

    @Test
    fun modelsInboxTest() {

        val fetcher = client?.messagesClient?.inbox(limit = LIMIT)
        val messages = fetcher?.fetchNext()
        assertNotEquals("Assert that messages in inbox are not null", null, messages)
        assertTrue("Assert that messages in inbox are not empty", messages?.isNotEmpty() ?: false)
    }

    @Test
    fun modelsCommentsTest() {

        val fetcher = client?.subredditsClient?.all(limit = LIMIT)
        val submissions = fetcher?.fetchNext()

        val randomSub = submissions?.random()

        assertNotEquals("Assert that this random submission is not null", null, randomSub)

        val fetcherComm = client?.contributionsClient?.comments(randomSub!!.id)
        val comments = fetcherComm?.fetchNext()

        assertNotEquals("Assert that comments in submission are not null", null, comments)
        assertTrue("Assert that comments are not empty", comments?.isNotEmpty() ?: false)
    }

    @After
    fun onPost() {
        bearer.revokeToken()
    }
}
