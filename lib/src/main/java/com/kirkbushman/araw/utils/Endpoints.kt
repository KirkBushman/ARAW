package com.kirkbushman.araw.utils

object Endpoints {

    const val URL_ME = "/api/v1/me/.json"
    const val URL_MY_BLOCKED = "/api/v1/me/blocked/.json"
    const val URL_MY_FRIENDS = "/api/v1/me/friends/.json"
    const val URL_MY_KARMA = "/api/v1/me/karma/.json"
    const val URL_MY_PREFS = "/api/v1/me/prefs/.json"
    const val URL_MY_TROPHIES = "/api/v1/me/trophies/.json"

    const val URL_SUBSCRIBE = "/api/subscribe"
    const val URL_SUBMIT = "/api/submit"
    const val URL_VOTE = "/api/vote"
    const val URL_REPLY = "/api/comment"
    const val URL_DELETE = "/api/del"
    const val URL_MARK_NSFW = "/api/marknsfw"
    const val URL_UNMARK_NSFW = "/api/unmarknsfw"
    const val URL_MARK_SPOILER = "/api/spoiler"
    const val URL_UNMARK_SPOILER = "/api/unspoiler"
    const val URL_SAVE = "/api/save"
    const val URL_UNSAVE = "/api/unsave"
    const val URL_HIDE = "/api/hide"
    const val URL_UNHIDE = "/api/unhide"
    const val URL_LOCK = "/api/lock"
    const val URL_UNLOCK = "/api/unlock"
    const val URL_FRIEND = "/api/friend"
    const val URL_UNFRIEND = "/api/unfriend"
    const val URL_COMMENT = "/api/info/.json"
    const val URL_SUBMISSION = "/api/info/.json"
    const val URL_MORECHILDREN = "/api/morechildren"
    const val URL_UPLOAD_ASSET = "/api/media/asset.json"

    const val URL_SUBMISSIONS_FRONTPAGE = "/{sorting}/.json"
    const val URL_SUBMISSIONS = "/r/{subreddit}/{sorting}/.json"
    const val URL_COMMENTS = "/comments/{submissionId}/.json"

    const val URL_FETCH_MESSAGES = "/message/{where}/.json"
    const val URL_DEL_MESSAGE = "/api/del_msg"
    const val URL_READ_ALL_MESSAGES = "/api/read_all_messages"
    const val URL_READ_MESSAGE = "/api/read_message"
    const val URL_UNREAD_MESSAGE = "/api/unread_message"

    const val URL_MULTI = "/user/{username}/m/{multiname}/{sorting}"
    const val URL_MULTIS_MINE = "/api/multi/mine.json"
    const val URL_MULTIS_REDDITOR = "/api/multi/user/{username}/.json"

    const val URL_SUBREDDIT = "/r/{subreddit}/about/.json"
    const val URL_SUBREDDITS = "/api/info/.json"
    const val URL_SUBREDDIT_INFO = "/r/{subreddit}/about/{where}/.json"
    const val URL_SUBREDDIT_RULES = "/r/{subreddit}/about/rules/.json"
    const val URL_SUBREDDIT_FLAIRS = "/r/{subreddit}/api/link_flair_v2/.json"
    const val URL_REDDITOR_SUBREDDITS = "/subreddits/mine/{where}/.json"

    const val URL_REDDITOR = "/user/{username}/about/.json"
    const val URL_REDDITOR_OVERVIEW = "/user/{username}/.json"
    const val URL_REDDITOR_WHERE = "/user/{username}/{where}/.json"
    const val URL_REDDITOR_MODERATED = "/user/{username}/moderated_subreddits/.json"
    const val URL_REDDITOR_TROPHIES = "/api/v1/user/{username}/trophies/.json"

    const val URL_WIKI = "/r/{subreddit}/wiki/.json"
    const val URL_WIKI_PAGE = "/r/{subreddit}/wiki/{page}/.json"
    const val URL_WIKI_PAGES = "/r/{subreddit}/wiki/pages/.json"
    const val URL_WIKI_REVISION = "/r/{subreddit}/wiki/revisions/{page}/.json"
    const val URL_WIKI_REVISIONS = "/r/{subreddit}/wiki/revisions/.json"

    const val URL_SEARCH = "/search/.json"
    const val URL_SEARCH_SUBREDDIT = "/r/{subreddit}/search/.json"
}
