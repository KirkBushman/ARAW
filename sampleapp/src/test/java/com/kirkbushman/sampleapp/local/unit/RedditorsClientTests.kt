package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.RedditorsClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class RedditorsClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: RedditorsClient

    @Before
    fun onPre() {

        client = RedditorsClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_genericWhere() {

        val fetcher = client.createContributionsFetcher("test_username", "test_string")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("test_string", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_overview() {

        val fetcher = client.createOverviewContributionsFetcher("test_username")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("overview", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_submitted() {

        val fetcher = client.createSubmittedContributionsFetcher("test_username")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("submitted", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_comments() {

        val fetcher = client.createCommentsContributionsFetcher("test_username")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("comments", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_gilded() {

        val fetcher = client.createGildedContributionsFetcher("test_username")

        Assert.assertEquals("test_username", fetcher.getUsername())
        Assert.assertEquals("gilded", fetcher.getWhere())
    }
}
