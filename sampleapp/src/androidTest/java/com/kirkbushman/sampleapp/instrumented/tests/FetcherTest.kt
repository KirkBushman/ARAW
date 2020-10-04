package com.kirkbushman.sampleapp.instrumented.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.sampleapp.instrumented.TestUtils
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FetcherTest {

    companion object {

        private const val LIMIT = 30L
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
    fun testFetcher() {
        val fetcher = client?.subredditsClient?.all(limit = LIMIT)

        assertTrue("Starting index should be null", fetcher?.getPageNum() == null)
        assertTrue("Initially hasStarted should not be true", !(fetcher?.hasStarted() ?: false))

        val listOne = fetcher?.fetchNext()

        assertTrue("Current Index should be 1", fetcher?.getPageNum() == 1)
        assertTrue("hasStarted should be true", (fetcher?.hasStarted() ?: false))

        assertNotEquals("Assert list is not null", null, listOne)
        assertTrue("Assert list is not empty", listOne?.isNotEmpty() ?: false)

        val listTwo = fetcher?.fetchNext()

        assertTrue("Current Index should be 2", fetcher?.getPageNum() == 2)
        assertTrue("hasStarted should be true", (fetcher?.hasStarted() ?: false))

        assertNotEquals("Assert list is not null", null, listTwo)
        assertTrue("Assert list is not empty", listTwo?.isNotEmpty() ?: false)

        val listThree = fetcher?.fetchNext()

        assertTrue("Current Index should be 3", fetcher?.getPageNum() == 3)
        assertTrue("hasStarted should be true", (fetcher?.hasStarted() ?: false))

        assertNotEquals("Assert list is not null", null, listThree)
        assertTrue("Assert list is not empty", listThree?.isNotEmpty() ?: false)

        val listTwoBack = fetcher?.fetchPrevious()

        assertTrue("Current Index should be 2", fetcher?.getPageNum() == 2)
        assertTrue("hasStarted should be true", (fetcher?.hasStarted() ?: false))

        assertNotEquals("Assert list is not null", null, listTwoBack)
        assertTrue("Assert list is not empty", listTwoBack?.isNotEmpty() ?: false)

        val listOneBack = fetcher?.fetchPrevious()

        assertTrue("Current Index should be 1", fetcher?.getPageNum() == 1)
        assertTrue("hasStarted should be true", (fetcher?.hasStarted() ?: false))

        assertNotEquals("Assert list is not null", null, listOneBack)
        assertTrue("Assert list is not empty", listOneBack?.isNotEmpty() ?: false)

        // -----------------------------

        /*if (listOne?.size ?: 0 == listOneBack?.size ?: 0) {
            assertTrue(
                "Assert that listOne and listOneBack are the same (Deep equality)",
                compareSubmissionsLists(listOne ?: emptyList(), listOneBack ?: emptyList())
            )
        }

        if (listTwo?.size ?: 0 == listTwoBack?.size ?: 0) {
            assertTrue(
                "Assert that listTwo and listTwoBack are the same (Deep equality)",
                compareSubmissionsLists(listTwo ?: emptyList(), listTwoBack ?: emptyList())
            )
        }*/
    }

    @After
    fun onPost() {
        bearer.revokeToken()
    }

    private fun compareSubmissionsLists(list1: List<Submission>, list2: List<Submission>): Boolean {

        if (list1.size != list2.size) {
            return false
        }

        list1.zip(list2).forEach { (item1, item2) ->

            if (item1.fullname != item2.fullname) {
                return false
            }
        }

        return true
    }
}
