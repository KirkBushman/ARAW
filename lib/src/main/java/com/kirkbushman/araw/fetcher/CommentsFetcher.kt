package com.kirkbushman.araw.fetcher

import androidx.annotation.IntRange
import androidx.annotation.WorkerThread
import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedCommentData
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.enums.CommentsSorting
import com.kirkbushman.araw.models.base.CommentData

class CommentsFetcher(

    private val api: RedditApi,
    private val submissionId: String,
    private val focusedCommentId: String? = null,
    private val focusedCommentParentsNum: Int? = null,

    private var sorting: CommentsSorting,

    private val depth: Int?,

    @IntRange(from = MIN_LIMIT, to = MAX_LIMIT)
    limit: Long = DEFAULT_LIMIT,

    private val disableLegacyEncoding: Boolean = false,

    private inline val getHeader: () -> Map<String, String>

) : Fetcher<CommentData>(limit) {

    companion object {

        val DEFAULT_SORTING = CommentsSorting.BEST
    }

    private var submission: Submission? = null

    @Suppress("UNCHECKED_CAST")
    @WorkerThread
    override fun onFetching(
        previousToken: String?,
        nextToken: String?,
        setTokens: (previous: String?, next: String?) -> Unit
    ): List<CommentData>? {
        val req = api.fetchComments(
            submissionId = submissionId,
            focusedCommentId = focusedCommentId,
            focusedCommentParentsNum = focusedCommentParentsNum,
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

        val resultBody = res.body()
        setTokens(null, null)

        submission = resultBody
            ?.firstOrNull()
            ?.data
            ?.children
            ?.firstOrNull()
            ?.data as Submission?

        return (
            resultBody
                ?.lastOrNull()
                ?.data as Listing<EnvelopedCommentData>?
            )
            ?.children
            ?.map { it.data }
            ?.toList()
    }

    fun getSubmission(): Submission? {
        return submission
    }

    fun getFocusedCommentId(): String? {
        return focusedCommentId
    }

    fun getSorting(): CommentsSorting = sorting
    fun setSorting(newSorting: CommentsSorting) {
        sorting = newSorting

        reset()
    }
}
