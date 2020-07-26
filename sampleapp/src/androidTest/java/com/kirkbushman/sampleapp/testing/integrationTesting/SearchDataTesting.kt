package com.kirkbushman.sampleapp.testing.integrationTesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.sampleapp.testing.TestUtils
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchDataTesting {

    companion object {

        private const val LIMIT = 100L
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
    fun onCompleteSearchResult() {

        var exception: Exception? = null

        try {

            val fetcher = client?.contributionsClient?.submissionsSearch(
                subreddit = "news",
                query = "news",
                limit = LIMIT,
                restrictToSubreddit = true
            )

            val submissions = fetcher?.fetchNext()
            assertNotEquals(
                "Assert that submissions from the search result are not null",
                null,
                submissions
            )
            assertTrue(
                "Assert that submissions from the search result are not empty",
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
    fun onEmptySearchResult() {

        var exception: Exception? = null

        try {

            val fetcher = client?.contributionsClient?.submissionsSearch(
                subreddit = "news",
                query = "sakifjewijr3o4jr8fs9dfsadofishadfkjasdhfskaldjfhasdkjlfhsadklfhasdfjksdbcsc7738783",
                limit = LIMIT,
                restrictToSubreddit = true
            )

            val submissions = fetcher?.fetchNext()
            assertNotEquals(
                "Assert that submissions from the search result are not null",
                null,
                submissions
            )
            assertTrue("Assert that submissions from the search result are empty", submissions?.isEmpty() ?: false)
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
