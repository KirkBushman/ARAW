package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.fetcher.CommentsFetcher
import com.kirkbushman.araw.fetcher.Fetcher
import com.kirkbushman.araw.fetcher.SubmissionsFetcher
import com.kirkbushman.araw.fetcher.SubmissionsSearchFetcher
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.MoreComments
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.TrendingSubreddits
import com.kirkbushman.araw.models.general.SearchSorting
import com.kirkbushman.araw.models.general.SubmissionsSorting
import com.kirkbushman.araw.models.general.TimePeriod
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.CommentData
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.araw.models.mixins.Replyable
import com.kirkbushman.araw.models.mixins.Votable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MultipartBody

class ContributionsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    fun submission(submissionId: String, disableLegacyEncoding: Boolean = false): Submission? {

        val authMap = getHeaderMap()
        val req = api.submission(
            submissionId = "t3_$submissionId",
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        // take the first in the listing
        return res.body()?.data?.children?.first()?.data
    }

    fun submissions(

        subreddit: Subreddit,
        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false
    ): SubmissionsFetcher {

        return submissions(subreddit.displayName, limit, sorting, timePeriod, disableLegacyEncoding)
    }

    fun submissions(

        subreddit: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsFetcher {
        return SubmissionsFetcher(

            api = api,
            subreddit = subreddit,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun submissionsSearch(

        subreddit: String?,
        query: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SearchSorting = SubmissionsSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsSearchFetcher.DEFAULT_TIMEPERIOD,

        disableLegacyEncoding: Boolean = false

    ): SubmissionsSearchFetcher {
        return SubmissionsSearchFetcher(

            api = api,
            subreddit = subreddit,
            query = query,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun comment(commentId: String, disableLegacyEncoding: Boolean = false): Comment? {

        val authMap = getHeaderMap()
        val req = api.comment(
            commentId = "t1_$commentId",
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children?.first()?.data
    }

    fun comments(

        submissionId: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        depth: Int? = null,
        disableLegacyEncoding: Boolean = false

    ): CommentsFetcher {

        return CommentsFetcher(
            api = api,
            submissionId = submissionId,
            limit = limit,
            depth = depth,
            disableLegacyEncoding = disableLegacyEncoding,
            getHeader = getHeaderMap
        )
    }

    fun moreChildren(

        moreComments: MoreComments,
        submission: Submission,

        limitChildren: Boolean? = null,
        depth: Int? = null,

        disableLegacyEncoding: Boolean = false

    ): List<CommentData>? {

        val authMap = getHeaderMap()
        val req = api.moreChildren(
            children = moreComments.children.joinToString(separator = ","),
            limitChildren = limitChildren,
            depth = depth,
            id = moreComments.id,
            linkId = submission.fullname,
            rawJson = (if (disableLegacyEncoding) 1 else null),
            header = authMap
        )

        val res = req.execute()
        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.json?.data?.things?.map { it.data }?.toList()
    }

    fun trendingSubreddits(): TrendingSubreddits? {

        val req = api.trendingSubreddits()
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun delete(comment: Comment): Any? {
        return delete(comment.fullname)
    }

    fun delete(submission: Submission): Any? {
        return delete(submission.fullname)
    }

    fun delete(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.delete(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun hide(submission: Submission): Any? {
        return hide(!submission.isHidden, submission.fullname)
    }

    fun hide(hide: Boolean, submission: Submission): Any? {
        return hide(hide, submission)
    }

    fun hide(hide: Boolean, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = if (hide)
            api.hide(fullname, header = authMap)
        else
            api.unhide(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun lock(comment: Comment): Any? {
        return lock(!comment.isLocked, comment.fullname)
    }

    fun lock(lock: Boolean, comment: Comment): Any? {
        return lock(lock, comment.fullname)
    }

    fun lock(submission: Submission): Any? {
        return lock(!submission.isLocked, submission.fullname)
    }

    fun lock(lock: Boolean, submission: Submission): Any? {
        return lock(lock, submission.fullname)
    }

    fun lock(lock: Boolean, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = if (lock)
            api.lock(fullname, header = authMap)
        else
            api.unlock(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun reply(replyable: Replyable, text: String): Comment? {
        return reply(replyable.fullname, text)
    }

    fun reply(fullname: String, text: String): Comment? {

        val authMap = getHeaderMap()
        val req = api.reply(
            fullname = fullname,
            text = text,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.json?.data?.things?.first()?.data
    }

    fun save(save: Boolean, contribution: Contribution): Any? {
        return save(save, contribution.fullname)
    }

    fun save(save: Boolean, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = if (save)
            api.save(id = fullname, header = authMap)
        else
            api.unsave(id = fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun vote(vote: Vote, votable: Votable): Any? {
        return vote(vote, votable.fullname)
    }

    fun vote(vote: Vote, fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.vote(id = fullname, dir = vote.dir, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun markAsNsfw(comment: Comment): Any? {
        return markAsNsfw(comment.fullname)
    }

    fun markAsNsfw(submission: Submission): Any? {
        return markAsNsfw(submission.fullname)
    }

    fun markAsNsfw(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.markAsNsfw(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun unmarkAsNsfw(submission: Submission): Any? {
        return unmarkAsNsfw(submission.fullname)
    }

    fun unmarkAsNsfw(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.unmarknsfw(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun markAsSpoiler(comment: Comment): Any? {
        return markAsSpoiler(comment.fullname)
    }

    fun markAsSpoiler(submission: Submission): Any? {
        return markAsSpoiler(submission.fullname)
    }

    fun markAsSpoiler(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.markAsSpoiler(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun unspoiler(submission: Submission): Any? {
        return unspoiler(submission.fullname)
    }

    fun unspoiler(fullname: String): Any? {

        val authMap = getHeaderMap()
        val req = api.unspoiler(fullname, header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun uploadMedia(filename: String, mimeType: String? = null, fileContent: ByteArray): String? {

        var currentMimeType = mimeType
        if (currentMimeType == null) {

            val extension = filename.substring(filename.lastIndexOf('.'))
            currentMimeType = when (extension) {
                "png" -> "image/png"
                "jpg" -> "image/jpeg"
                "jpeg" -> "image/jpeg"
                "gif" -> "image/gif"
                "webp" -> "image/webp"
                "mp4" -> "video/mp4"
                "mpeg" -> "video/mpeg"
                "webm" -> "video/webm"

                else -> throw IllegalStateException("Media's mimetype not supported! check the file extension.")
            }
        }

        val authMap = getHeaderMap()
        val req = api.obtainUploadContract(
            filepath = filename,
            mimetype = currentMimeType,
            header = authMap
        )

        val uploadContractRes = req.execute()
        if (!uploadContractRes.isSuccessful) {
            return null
        }

        val uploadContract = uploadContractRes.body() ?: return null
        val uploadData = uploadContract.toUploadData()

        val requestBody = fileContent.toRequestBody(currentMimeType.toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("file", filename, requestBody)

        val uploadUrl = "https:".plus(uploadContract.args.action)

        val multipartMediaType = "multipart/form-data".toMediaType()
        val req2 = api.uploadMedia(
            uploadUrl = uploadUrl,
            acl = uploadData.acl.toRequestBody(multipartMediaType),
            key = uploadData.key.toRequestBody(multipartMediaType),
            xAmzCredential = uploadData.xAmzCredential.toRequestBody(multipartMediaType),
            xAmzAlgorithm = uploadData.xAmzAlgorithm.toRequestBody(multipartMediaType),
            xAmzDate = uploadData.xAmzDate.toRequestBody(multipartMediaType),
            successActionStatus = uploadData.successActionStatus.toRequestBody(multipartMediaType),
            contentType = uploadData.contentType.toRequestBody(multipartMediaType),
            xAmzStorageClass = uploadData.xAmzStorageClass.toRequestBody(multipartMediaType),
            xAmzMetaExt = uploadData.xAmzMetaExt.toRequestBody(multipartMediaType),
            policy = uploadData.policy.toRequestBody(multipartMediaType),
            xAmzSignature = uploadData.xAmzSignature.toRequestBody(multipartMediaType),
            xAmzSecurityToken = uploadData.xAmzSecurityToken.toRequestBody(multipartMediaType),
            file = multipartBody
        )

        val res2 = req2.execute()
        if (!res2.isSuccessful) {
            return null
        }

        return uploadUrl.plus('/').plus(uploadData.key)
    }
}
