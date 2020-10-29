package com.kirkbushman.sampleapp.local.tests

import com.kirkbushman.araw.fetcher.Fetcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class FetcherTest {

    @Test
    fun testFetcher_hasStarted() {

        val fetcher = Mockito.mock(Fetcher::class.java, Mockito.CALLS_REAL_METHODS)

        assertFalse("Assert that initially returns false", fetcher.hasStarted())

        fetcher.fetchNext()

        assertTrue("Assert that initially returns true", fetcher.hasStarted())
    }

    @Test
    fun testFetcher_PageNumUpDown() {

        val fetcher = Mockito.mock(Fetcher::class.java)

        assertNull("Assert that initially the counter is null", fetcher.getPageNum())

        fetcher.fetchNext()

        Mockito.doReturn(true).`when`(fetcher).hasNext()
        Mockito.doReturn(false).`when`(fetcher).hasPrevious()

        assertEquals("Assert that counter went up", 1, fetcher.getPageNum())

        fetcher.fetchNext()

        Mockito.doReturn(true).`when`(fetcher).hasPrevious()

        assertEquals("Assert that counter went up", 2, fetcher.getPageNum())

        fetcher.fetchPrevious()

        Mockito.doReturn(false).`when`(fetcher).hasPrevious()

        assertEquals("Assert that counter went down", 1, fetcher.getPageNum())

        fetcher.fetchPrevious()

        assertEquals("Assert that counter stays the same", 1, fetcher.getPageNum())
    }

    @Test
    fun testFetcher_PageNumReset() {

        val fetcher = Mockito.mock(Fetcher::class.java)

        assertNull("Assert that initially the counter is null", fetcher.getPageNum())

        fetcher.fetchNext()

        assertNotNull("Assert that the counter is not null", fetcher.getPageNum())

        fetcher.reset()

        assertNull("Assert that the counter is null", fetcher.getPageNum())
    }

    @Test
    fun testFetcher_TokensReset() {

        val fetcher = Mockito.mock(Fetcher::class.java)

        assertNull("Assert that initially the token is null", fetcher.getNextToken())
        assertNull("Assert that initially the token is null", fetcher.getPreviousToken())

        fetcher.fetchNext()

        Mockito.doReturn("FAKE_TOKEN").`when`(fetcher).getNextToken()
        Mockito.doReturn("FAKE_TOKEN").`when`(fetcher).getPreviousToken()

        assertNotNull("Assert that the token is not null", fetcher.getNextToken())
        assertNotNull("Assert that the token is not null", fetcher.getPreviousToken())

        Mockito.reset(fetcher)
        fetcher.reset()

        assertNull("Assert that the token is null", fetcher.getNextToken())
        assertNull("Assert that the token is null", fetcher.getPreviousToken())
    }
}
