package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedCommentData
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.CommentsSorting
import com.kirkbushman.araw.models.mixins.CommentData

class CommentsFetcher(

    private val api: RedditApi,
    private val submissionId: String,

    private var sorting: CommentsSorting,

    private val depth: Int?,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<CommentData, EnvelopedCommentData>(limit) {

    companion object {

        val DEFAULT_SORTING = CommentsSorting.BEST
    }

    private var submission: Submission? = null

    @Suppress("UNCHECKED_CAST")
    @WorkerThread
    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedCommentData>? {

        val req = api.fetchComments(
            submissionId = submissionId,
            sorting = getSorting().sortingStr,
            limit = getLimit(),
            depth = depth,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = getHeader()
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        val body = res.body()

        submission = body?.firstOrNull()?.data?.children?.firstOrNull()?.data as Submission?

        return body?.last()?.data as Listing<EnvelopedCommentData>?
    }

    override fun onMapResult(pagedData: Listing<EnvelopedCommentData>?): List<CommentData> {

        if (pagedData == null) {
            return listOf()
        }

        return pagedData
            .children
            .map { it.data }
            .toList()
    }

    fun getSubmission(): Submission? {
        return submission
    }

    fun getSorting(): CommentsSorting = sorting
    fun setSorting(newSorting: CommentsSorting) {
        sorting = newSorting

        reset()
    }
}
