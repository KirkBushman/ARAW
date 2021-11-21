package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.ContributionsClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class ContributionsClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: ContributionsClient

    @Before
    fun onPre() {

        client = ContributionsClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_generic() {

        val fetcher = client.createSubmissionsFetcher("test_string")

        Assert.assertEquals("test_string", fetcher.getSubreddit())
    }
}
