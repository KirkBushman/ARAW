package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.InboxFetcher
import com.kirkbushman.araw.models.Message
import com.kirkbushman.araw.models.enums.Vote

class MessagesClient(

    private val api: RedditApi,
    private val disableLegacyEncoding: Boolean,
    private inline val getHeaderMap: () -> Map<String, String>

) {

    fun createOverviewInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_INBOX,
            limit = limit
        )
    }

    fun createUnreadInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_UNREAD,
            limit = limit
        )
    }

    fun createMessagesInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_MESSAGES,
            limit = limit
        )
    }

    fun createSentInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_SENT,
            limit = limit
        )
    }

    fun createCommentsRepliesInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_COMMENTS,
            limit = limit
        )
    }

    fun createSelfRepliesInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_SELF_REPLY,
            limit = limit
        )
    }

    fun createMentionsInboxFetcher(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return createInboxFetcher(
            where = InboxFetcher.MESSAGES_MENTIONS,
            limit = limit
        )
    }

    fun createInboxFetcher(

        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT

    ): InboxFetcher {

        return InboxFetcher(
            api = api,
            where = where,
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    @WorkerThread
    fun deleteMessage(message: Message): Any? {
        return deleteMessage(message.fullname)
    }

    @WorkerThread
    fun deleteMessage(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.deleteMessage(
            id = fullname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun markAsRead(read: Boolean, message: Message): Any? {
        return markAsRead(read, message.fullname)
    }

    @WorkerThread
    fun markAsRead(read: Boolean, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = if (read) {
            api.readMessage(
                id = fullname,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = authMap
            )
        } else {
            api.unreadMessage(
                id = fullname,
                rawJson = (if (disableLegacyEncoding) 1 else null),
                header = authMap
            )
        }

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun vote(vote: Vote, message: Message): Any? {
        return vote(vote, message.fullname)
    }

    @WorkerThread
    fun vote(vote: Vote, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.vote(
            id = fullname,
            dir = vote.dir,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun markAllAsRead(filters: Array<Message>?): Any? {

        return markAllAsRead(filters?.joinToString(separator = ",") { it.fullname })
    }

    @WorkerThread
    fun markAllAsRead(filters: List<Message>?): Any? {

        return markAllAsRead(filters?.joinToString(separator = ",") { it.fullname })
    }

    @WorkerThread
    fun markAllAsRead(filters: String?): Any? {

        val authMap = getHeaderMap()
        val req = api.readAllMessages(
            filters = filters ?: "",
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }
}
