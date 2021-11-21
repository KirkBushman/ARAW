package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.AccountsClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class AccountsClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: AccountsClient

    @Before
    fun onPre() {

        client = AccountsClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_genericWhere() {

        val fetcher = client.createContributionsFetcher("test_string")

        assertEquals("test_string", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_overview() {

        val fetcher = client.createOverviewContributionsFetcher()

        assertEquals("overview", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_submitted() {

        val fetcher = client.createSubmittedContributionsFetcher()

        assertEquals("submitted", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_comments() {

        val fetcher = client.createCommentsContributionsFetcher()

        assertEquals("comments", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_upvoted() {

        val fetcher = client.createUpvotedContributionsFetcher()

        assertEquals("upvoted", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_downvoted() {

        val fetcher = client.createDownvotedContributionsFetcher()

        assertEquals("downvoted", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_hidden() {

        val fetcher = client.createHiddenContributionsFetcher()

        assertEquals("hidden", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_saved() {

        val fetcher = client.createSavedContributionsFetcher()

        assertEquals("saved", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_gilded() {

        val fetcher = client.createGildedContributionsFetcher()

        assertEquals("gilded", fetcher.getWhere())
    }

    @Test
    fun testCreateSubredditsFetcher_genericWhere() {

        val fetcher = client.createSubredditsFetcher("test_string")

        assertEquals("test_string", fetcher.getWhere())
    }

    @Test
    fun testCreateSubredditsFetcher_subscribed() {

        val fetcher = client.createSubscribedSubredditsFetcher()

        assertEquals("subscriber", fetcher.getWhere())
    }

    @Test
    fun testCreateSubredditsFetcher_contributor() {

        val fetcher = client.createContributedSubredditsFetcher()

        assertEquals("contributor", fetcher.getWhere())
    }

    @Test
    fun testCreateSubredditsFetcher_moderator() {

        val fetcher = client.createModeratedSubredditsFetcher()

        assertEquals("moderator", fetcher.getWhere())
    }

    @Test
    fun testCreateSubredditsFetcher_streams() {

        val fetcher = client.createStreamsSubredditsFetcher()

        assertEquals("streams", fetcher.getWhere())
    }
}
