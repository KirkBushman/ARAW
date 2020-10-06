package com.kirkbushman.araw.clients

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.InboxFetcher
import com.kirkbushman.araw.models.Message
import com.kirkbushman.araw.models.general.Vote

class MessagesClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>
) {

    fun inbox(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "inbox",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun unread(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "unread",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun messages(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "messages",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun sent(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "sent",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun commentsReplies(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "comments",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun selfReplies(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "selfreply",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun mentions(

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

    ): InboxFetcher {

        return fetchMessages(
            where = "mentions",
            limit = limit,
            disableLegacyEncoding = disableLegacyEncoding
        )
    }

    fun fetchMessages(

        where: String,

        @IntRange(from = Fetcher.MIN_LIMIT, to = Fetcher.MAX_LIMIT)
        limit: Long = Fetcher.DEFAULT_LIMIT,

        disableLegacyEncoding: Boolean = false

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
        val req = api.deleteMessage(id = fullname, header = authMap)
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
        val req = if (read)
            api.readMessage(id = fullname, header = authMap)
        else
            api.unreadMessage(id = fullname, header = authMap)

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
        val req = api.vote(id = fullname, dir = vote.dir, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    @WorkerThread
    fun markAllAsRead(filters: List<Message>?): Any? {

        return markAllAsRead(filters?.joinToString(separator = ",") { it.fullname })
    }

    @WorkerThread
    fun markAllAsRead(filters: String?): Any? {

        val authMap = getHeaderMap()
        val req = api.readAllMessages(filters = filters ?: "", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }
}
