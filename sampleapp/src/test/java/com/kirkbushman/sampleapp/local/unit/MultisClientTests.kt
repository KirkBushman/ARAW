package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.MultisClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MultisClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: MultisClient

    @Before
    fun onPre() {

        client = MultisClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_genericWhere() {

        val fetcher = client.createMultiSubmissionsFetcher("test_username", "test_multiname")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("test_multiname", fetcher.getMultiname())
    }
}
