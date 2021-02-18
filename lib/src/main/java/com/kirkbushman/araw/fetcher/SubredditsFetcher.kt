package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.base.SubredditData

class SubredditsFetcher(

    private val api: RedditApi,
    private val where: String,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<SubredditData>(limit) {

    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (next: String?, previous: String?) -> Unit
    ): List<SubredditData>? {

        val req = api.fetchRedditorSubreddits(
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
        setTokens(resultBody?.data?.after, resultBody?.data?.before)

        return resultBody
            ?.data
            ?.children
            ?.map { it.data }
            ?.toList()
    }
}
