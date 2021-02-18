package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.fetcher.Fetcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class FetcherTest {

    private lateinit var fetcher: Fetcher<*>

    @Before
    fun onPre() {

        val settings = Mockito
            .withSettings()
            .useConstructor(25L)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)

        fetcher = Mockito.mock(Fetcher::class.java, settings)
    }

    @Test
    fun testFetcher_hasStarted() {

        assertFalse(fetcher.hasStarted())

        fetcher.fetchNext()

        assertTrue(fetcher.hasStarted())

        fetcher.fetchNext()

        assertTrue(fetcher.hasStarted())

        fetcher.reset()

        assertFalse(fetcher.hasStarted())
    }

    /*@Test
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
    }*/

    @Test
    fun testFetcher_PageNumReset() {

        assertEquals(-1, fetcher.getPageNum())

        fetcher.fetchNext()

        assertEquals(1, fetcher.getPageNum())

        fetcher.fetchNext()

        assertEquals(2, fetcher.getPageNum())

        fetcher.reset()

        assertEquals(-1, fetcher.getPageNum())
    }

    @Test
    fun testFetcher_TokensReset() {

        assertNull("Assert that initially the token is null", fetcher.getNextToken())
        assertNull("Assert that initially the token is null", fetcher.getPreviousToken())

        fetcher.fetchNext()

        Mockito.doReturn("FAKE_TOKEN_NEXT").`when`(fetcher).getNextToken()
        Mockito.doReturn("FAKE_TOKEN_PREV").`when`(fetcher).getPreviousToken()

        assertNotNull("Assert that the token is not null", fetcher.getNextToken())
        assertNotNull("Assert that the token is not null", fetcher.getPreviousToken())

        assertEquals("FAKE_TOKEN_NEXT", fetcher.getNextToken())
        assertEquals("FAKE_TOKEN_PREV", fetcher.getPreviousToken())

        Mockito.reset(fetcher)
        fetcher.reset()

        assertNull("Assert that the token is null", fetcher.getNextToken())
        assertNull("Assert that the token is null", fetcher.getPreviousToken())
    }
}
