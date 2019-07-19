package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedMessage
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.MessageListing
import com.kirkbushman.araw.models.Message

class InboxFetcher(

    private val api: RedditApi,
    private val where: String,

    limit: Int = DEFAULT_LIMIT,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<Message, EnvelopedMessage>(limit) {

    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedMessage>? {

        val req = api.fetchMessages(
            where = where,
            limit = if (forward) getLimit() else getLimit() + 1,
            count = getCount(),
            after = if (forward) dirToken else null,
            before = if (!forward) dirToken else null,
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

    override fun toString(): String {
        return "InboxFetcher { " +
                "where: $where, " +
                "${super.toString()} " +
                "}"
    }
}
