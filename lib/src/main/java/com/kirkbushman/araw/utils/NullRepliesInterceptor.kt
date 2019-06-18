package com.kirkbushman.araw.utils

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

object NullRepliesInterceptor : Interceptor {

    private const val TAG_BEFORE = "\"replies\": \"\""
    private const val TAG_AFTER = "\"replies\": null"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        var rawJson = response.body()?.string() ?: ""

        if (rawJson.contains(TAG_BEFORE)) {
            rawJson = rawJson.replace(TAG_BEFORE, TAG_AFTER)
        }

        return response.newBuilder()
            .body(ResponseBody.create(response.body()?.contentType(), rawJson)).build()
    }
}