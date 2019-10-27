package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.araw.models.general.SubmissionKind

class SubredditRedditClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>

) : BaseRedditClient(api, getHeaderMap) {

    fun submit(

        subredditName: String,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean = false,
        sendReplies: Boolean = false,
        isNsfw: Boolean = false,
        isSpoiler: Boolean = false

    ): Any? {

        val authMap = getHeaderMap()
        val req = api.submit(
            subreddit = subredditName,
            title = title,
            kind = kind.toString(),
            text = if (kind == SubmissionKind.self) text else null,
            url = if (kind == SubmissionKind.link) url else null,
            resubmit = resubmit,
            sendReplies = sendReplies,
            isNsfw = isNsfw,
            isSpoiler = isSpoiler,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }

    fun submit(

        subreddit: Subreddit,

        title: String,
        kind: SubmissionKind,

        text: String = "",
        url: String = "",

        resubmit: Boolean = false,
        sendReplies: Boolean = false,
        isNsfw: Boolean? = null,
        isSpoiler: Boolean = false

    ): Any? {

        val authMap = getHeaderMap()
        val req = api.submit(
            subreddit = subreddit.displayName,
            title = title,
            kind = kind.toString(),
            text = if (kind == SubmissionKind.self) text else null,
            url = if (kind == SubmissionKind.link) url else null,
            resubmit = resubmit,
            sendReplies = sendReplies,
            isNsfw = isNsfw ?: subreddit.over18,
            isSpoiler = isSpoiler,
            header = authMap
        )

        val res = req.execute()

        if (!res.isSuccessful) {
            return null
        }

        return res.body()
    }
}
