package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.InboxFetcher
import com.kirkbushman.araw.models.Message

class MessagesClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    fun inbox(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "inbox", limit) { getHeaderMap() }
    }

    fun unread(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "unread", limit) { getHeaderMap() }
    }

    fun messages(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "messages", limit) { getHeaderMap() }
    }

    fun sent(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "sent", limit) { getHeaderMap() }
    }

    fun commentsReplies(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "comments", limit) { getHeaderMap() }
    }

    fun selfReplies(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "selfreply", limit) { getHeaderMap() }
    }

    fun mentions(limit: Int = Fetcher.DEFAULT_LIMIT): InboxFetcher {
        return InboxFetcher(api, "mentions", limit) { getHeaderMap() }
    }

    fun deleteMessage(message: Message): Any? {
        return deleteMessage(message.fullname)
    }

    fun deleteMessage(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.deleteMessage(id = fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun markAsRead(read: Boolean, message: Message): Any? {
        return markAsRead(read, message.fullname)
    }

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

    fun markAllAsRead(filters: List<Message>?): Any? {

        return markAllAsRead(filters?.joinToString(separator = ",") { it.fullname })
    }

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
