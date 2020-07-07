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
        private val subreddits = arrayOf(
            "soccer",
            "askreddit",
            "todayilearned",
            "pics",
            "tifu",
            "bestof",
            "videos",
            "wtf",
            "learnpython",
            "crosspost",

            "Gunners",
            "PoliticalCompassMemes",
            "privacy",
            "europe",
            "androidapps"
        )

        private const val LIMIT = 100
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

        var exception: Exception? = null

        try {

            val me = client?.accountsClient?.me()
            assertNotEquals("Assert that Me Object is not null", null, me)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsFrontpageSubmissionsTest() {

        var exception: Exception? = null

        try {

            val fetcher = client?.subredditsClient?.frontpage(limit = LIMIT)
            val submissions = fetcher?.fetchNext()
            assertNotEquals("Assert that submissions from /r/frontpage are not null", null, submissions)
            assertTrue(
                "Assert that submissions from /r/frontpage are not empty",
                submissions?.isNotEmpty() ?: false
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsAllSubmissionsTest() {

        var exception: Exception? = null

        try {

            val fetcher = client?.subredditsClient?.all(limit = LIMIT)
            val submissions = fetcher?.fetchNext()
            assertNotEquals("Assert that submissions from /r/all are not null", null, submissions)
            assertTrue(
                "Assert that submissions from /r/all are not empty",
                submissions?.isNotEmpty() ?: false
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsSubmissionsTest() {

        var exception: Exception? = null

        try {

            subreddits.forEach {

                val fetcher = client?.contributionsClient?.submissions(it, limit = LIMIT)
                val submissions = fetcher?.fetchNext()
                assertNotEquals("Assert that submissions from /r/$it are not null", null, submissions)
                assertTrue(
                    "Assert that submissions from /r/$it are not empty",
                    submissions?.isNotEmpty() ?: false
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsSubredditTest() {

        var exception: Exception? = null

        try {

            subreddits.forEach {

                val subreddit = client?.subredditsClient?.subreddit(it)
                assertNotEquals("Assert that subreddit models from /r/$it are not null", null, subreddit)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsInboxSentTest() {

        var exception: Exception? = null

        try {

            val fetcher = client?.messagesClient?.sent(limit = LIMIT)
            val messages = fetcher?.fetchNext()
            assertNotEquals("Assert that messages in inbox are not null", null, messages)
            assertTrue("Assert that messages in inbox are not empty", messages?.isNotEmpty() ?: false)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @Test
    fun modelsInboxTest() {

        var exception: Exception? = null

        try {

            val fetcher = client?.messagesClient?.inbox(limit = LIMIT)
            val messages = fetcher?.fetchNext()
            assertNotEquals("Assert that messages in inbox are not null", null, messages)
            assertTrue("Assert that messages in inbox are not empty", messages?.isNotEmpty() ?: false)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
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

    @Test
    fun modelWikiPagesTest() {

        var exception: Exception? = null

        try {

            val randomSub = subreddits.random()
            val pages = client?.wikisClient?.wikiPages(subreddit = randomSub)
            assertNotEquals("Assert that pages in the sub wiki are not null", null, pages)
            assertTrue("Assert that pages in the sub wiki are not empty", pages?.isNotEmpty() ?: false)
        } catch (ex: Exception) {
            ex.printStackTrace()
            exception = ex
        } finally {
            assertNull("Check there are no exceptions thrown", exception)
        }
    }

    @After
    fun onPost() {
        bearer.revokeToken()
    }
}
