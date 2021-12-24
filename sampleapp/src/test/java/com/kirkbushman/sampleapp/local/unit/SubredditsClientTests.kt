package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.SubredditsClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class SubredditsClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: SubredditsClient

    @Before
    fun onPre() {

        client = SubredditsClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_genericWhere() {

        val fetcher = client.createSubmissionsFetcher("test_string")

        Assert.assertEquals("test_string", fetcher.getSubreddit())
    }

    @Test
    fun testCreateFetcher_frontpage() {

        val fetcher = client.createFrontpageSubmissionsFetcher()

        Assert.assertEquals("", fetcher.getSubreddit())
    }

    @Test
    fun testCreateFetcher_all() {

        val fetcher = client.createAllSubmissionsFetcher()

        Assert.assertEquals("all", fetcher.getSubreddit())
    }

    @Test
    fun testCreateFetcher_popular() {

        val fetcher = client.createPopularSubmissionsFetcher()

        Assert.assertEquals("popular", fetcher.getSubreddit())
    }

    @Test
    fun testCreateFetcher_friends() {

        val fetcher = client.createFriendsSubmissionsFetcher()

        Assert.assertEquals("friends", fetcher.getSubreddit())
    }

    @Test
    fun testCreateFetcher_original() {

        val fetcher = client.createOriginalSubmissionsFetcher()

        Assert.assertEquals("original", fetcher.getSubreddit())
    }

    @Test
    fun testDisableLegacyEncoding_coherence() {

        client = SubredditsClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )

        val fetcher = client.createFrontpageSubmissionsFetcher()

        Assert.assertEquals(false, fetcher.getDisableLegacyEncoding())

        client = SubredditsClient(
            api = mockApi,
            disableLegacyEncoding = true,
            getHeaderMap = { mapOf() }
        )

        val fetcher2 = client.createFrontpageSubmissionsFetcher()

        Assert.assertEquals(true, fetcher2.getDisableLegacyEncoding())
    }
}
