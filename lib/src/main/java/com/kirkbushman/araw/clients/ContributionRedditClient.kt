package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.Comment
import com.kirkbushman.araw.models.Submission
import com.kirkbushman.araw.models.general.Vote
import com.kirkbushman.araw.models.mixins.Contribution
import com.kirkbushman.araw.models.mixins.Replyable
import com.kirkbushman.araw.models.mixins.Votable

class ContributionRedditClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

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
}
