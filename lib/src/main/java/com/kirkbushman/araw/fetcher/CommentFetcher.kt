package com.kirkbushman.araw.fetcher

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.http.EnvelopedCommentData
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.mixins.CommentData

class CommentFetcher(

    private val api: RedditApi,
    private val submissionId: String,

    private val depth: Int?,
    limit: Int = DEFAULT_LIMIT,

    private inline val getHeader: () -> HashMap<String, String>

) : Fetcher<CommentData, EnvelopedCommentData>(limit) {

    private var submission: Submission? = null

    @Suppress("UNCHECKED_CAST")
    override fun onFetching(forward: Boolean, dirToken: String): Listing<EnvelopedCommentData>? {

        val req = api.fetchComments(
            submissionId = submissionId,
            limit = getLimit(),
            depth = depth,
            header = getHeader())
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        val body = res.body()

        submission = body?.first()?.data?.children?.first()?.data as Submission?

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
}