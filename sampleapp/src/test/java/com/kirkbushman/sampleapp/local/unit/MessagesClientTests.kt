package com.kirkbushman.sampleapp.local.unit

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.clients.MessagesClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MessagesClientTests {

    @Mock
    lateinit var mockApi: RedditApi

    lateinit var client: MessagesClient

    @Before
    fun onPre() {

        client = MessagesClient(
            api = mockApi,
            disableLegacyEncoding = false,
            getHeaderMap = { mapOf() }
        )
    }

    @Test
    fun testCreateFetcher_genericWhere() {

        val fetcher = client.createInboxFetcher("test_string")

        Assert.assertEquals("test_string", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_overview() {

        val fetcher = client.createOverviewInboxFetcher()

        Assert.assertEquals("inbox", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_unread() {

        val fetcher = client.createUnreadInboxFetcher()

        Assert.assertEquals("unread", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_messages() {

        val fetcher = client.createMessagesInboxFetcher()

        Assert.assertEquals("messages", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_sent() {

        val fetcher = client.createSentInboxFetcher()

        Assert.assertEquals("sent", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_comments() {

        val fetcher = client.createCommentsRepliesInboxFetcher()

        Assert.assertEquals("comments", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_selfReply() {

        val fetcher = client.createSelfRepliesInboxFetcher()

        Assert.assertEquals("selfreply", fetcher.getWhere())
    }

    @Test
    fun testCreateFetcher_mentions() {

        val fetcher = client.createMentionsInboxFetcher()

        Assert.assertEquals("mentions", fetcher.getWhere())
    }
}
