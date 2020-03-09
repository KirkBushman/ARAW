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

class ContributionsClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    fun submission(submissionId: String): Submission? {

        val authMap = getHeaderMap()
        val req = api.submission(submissionId = "t3_$submissionId", header = authMap)
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
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD
    ): SubmissionsFetcher {

        return submissions(subreddit.displayName, limit, sorting, timePeriod)
    }

    fun submissions(

        subreddit: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SubmissionsSorting = SubmissionsFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsFetcher {
        return SubmissionsFetcher(

            api = api,
            subreddit = subreddit,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = getHeaderMap
        )
    }

    fun submissionsSearch(

        subreddit: String?,
        query: String,

        limit: Int = Fetcher.DEFAULT_LIMIT,

        sorting: SearchSorting = SubmissionsSearchFetcher.DEFAULT_SORTING,
        timePeriod: TimePeriod = SubmissionsSearchFetcher.DEFAULT_TIMEPERIOD

    ): SubmissionsSearchFetcher {
        return SubmissionsSearchFetcher(

            api = api,
            subreddit = subreddit,
            query = query,
            limit = limit,
            sorting = sorting,
            timePeriod = timePeriod,
            getHeader = getHeaderMap
        )
    }

    fun comments(

        submissionId: String,
        limit: Int = Fetcher.DEFAULT_LIMIT,
        depth: Int? = null

    ): CommentsFetcher {

        return CommentsFetcher(api, submissionId, limit = limit, depth = depth, getHeader = getHeaderMap)
    }

    fun comment(commentId: String): Comment? {

        val authMap = getHeaderMap()
        val req = api.comment(commentId = "t1_$commentId", header = authMap)
        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()?.data?.children?.first()?.data
    }

    fun moreChildren(

        moreComments: MoreComments,
        submission: Submission,

        limitChildren: Boolean? = null,
        depth: Int? = null
    ): List<CommentData>? {

        val authMap = getHeaderMap()
        val req = api.moreChildren(
            children = "",// moreComments.children.joinToString(separator = ","),
            limitChildren = limitChildren,
            depth = depth,
            id = moreComments.id,
            linkId = submission.fullname,
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
}
