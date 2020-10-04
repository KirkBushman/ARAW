package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedMessage
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.MessageListing
import com.kirkbushman.araw.models.Message

class InboxFetcher(

    private val api: RedditApi,
    private val where: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Message, EnvelopedMessage>(limit) {

    @WorkerThread
    override fun onFetching(forward: Boolean, dirToken: String?): Listing<EnvelopedMessage>? {

        val req = api.fetchMessages(
            where = where,
            limit = getLimit(),
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data
    }

    override fun onMapResult(pagedData: Listing<EnvelopedMessage>?): List<Message> {

        if (pagedData == null) {
            return listOf()
        }

        return (pagedData as MessageListing)
            .children
            .map { it.data }
            .toList()
    }
}
