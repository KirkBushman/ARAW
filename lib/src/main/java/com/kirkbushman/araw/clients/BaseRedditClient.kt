package com.kirkbushman.araw.clients

import com.kirkbushman.araw.RedditApi

open class BaseRedditClient(

    private val api: RedditApi,
    private inline val getHeaderMap: () -> HashMap<String, String>
)
