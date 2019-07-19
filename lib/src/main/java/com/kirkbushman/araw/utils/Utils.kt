package com.kirkbushman.araw.utils

import com.kirkbushman.araw.http.EnvelopedComment
import com.kirkbushman.araw.http.EnvelopedCommentData
import com.kirkbushman.araw.http.EnvelopedContribution
import com.kirkbushman.araw.http.EnvelopedData
import com.kirkbushman.araw.http.EnvelopedMessage
import com.kirkbushman.araw.http.EnvelopedMoreComment
import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.EnvelopedSubmission
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Utils {

    private const val BASE_URL = "https://oauth.reddit.com"

    fun getRetrofit(logging: Boolean): Retrofit {

        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(EnvelopedData::class.java, "kind")
                    .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                    .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
                    .withSubtype(EnvelopedMessage::class.java, EnvelopeKind.Message.value)
                    .withSubtype(EnvelopedRedditor::class.java, EnvelopeKind.Account.value)
                    .withSubtype(EnvelopedSubmission::class.java, EnvelopeKind.Link.value)
                    .withSubtype(EnvelopedSubreddit::class.java, EnvelopeKind.Subreddit.value)
            )
            .add(
                PolymorphicJsonAdapterFactory.of(EnvelopedContribution::class.java, "kind")
                    .withSubtype(EnvelopedSubmission::class.java, EnvelopeKind.Link.value)
                    .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                    .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
            )
            .add(
                PolymorphicJsonAdapterFactory.of(EnvelopedCommentData::class.java, "kind")
                    .withSubtype(EnvelopedComment::class.java, EnvelopeKind.Comment.value)
                    .withSubtype(EnvelopedMoreComment::class.java, EnvelopeKind.More.value)
            )
            .build()

        val moshiFactory = MoshiConverterFactory.create(moshi)
        val nullRepliesInterceptor = NullRepliesInterceptor

        val httpClient = if (logging) {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient
                .Builder()
                .addInterceptor(nullRepliesInterceptor)
                .addInterceptor(logger)
                .build()
        } else {

            OkHttpClient
                .Builder()
                .addInterceptor(nullRepliesInterceptor)
                .build()
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiFactory)
            .client(httpClient)
            .build()
    }
}
