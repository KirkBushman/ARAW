package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.Message

class InboxFetcher(

    private val api: RedditApi,
    private val where: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> Map<String, String>

) : Fetcher<Message>(limit) {

    companion object {

        const val MESSAGES_INBOX = "inbox"
        const val MESSAGES_UNREAD = "unread"
        const val MESSAGES_MESSAGES = "messages"
        const val MESSAGES_SENT = "sent"
        const val MESSAGES_COMMENTS = "comments"
        const val MESSAGES_SELF_REPLY = "selfreply"
        const val MESSAGES_MENTIONS = "mentions"
    }

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<Message>? {

        val req = api.fetchMessages(
            where = where,
            limit = getLimit(),
            count = getCount(),
            before = previousToken,
            after = nextToken,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        val resultBody = res.body()
        setTokens(resultBody?.data?.before, resultBody?.data?.after)

        return resultBody
            ?.data
            ?.children
            ?.map { it.data }
    }

    fun getWhere(): String {
        return where
    }
}
