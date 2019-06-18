package com.kirkbushman.araw

import com.kirkbushman.araw.http.EnvelopedCommentListing
import com.kirkbushman.araw.http.EnvelopedContributionListing
import com.kirkbushman.araw.http.EnvelopedMessageListing
import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.EnvelopedSubmissionListing
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.EnvelopedSubredditListing
import com.kirkbushman.araw.http.EnvelopedTrophyList
import com.kirkbushman.araw.http.EnvelopedWikiPage
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.SubredditRules
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {

    @GET("/api/v1/me")
    fun me(
        @HeaderMap header: HashMap<String, String>
    ): Call<Me>

    @GET("/message/{where}/.json")
    fun fetchMessages(
        @Path("where") where: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedMessageListing>

    @GET("/user/{username}/about/.json")
    fun user(
        @Path("username") username: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedRedditor>

    @GET("/user/{username}/.json")
    fun fetchUserOverview(
        @Path("username") username: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedContributionListing>

    @GET("/user/{username}/{where}/.json")
    fun fetchUserInfo(
        @Path("username") username: String,
        @Path("where") where: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedContributionListing>

    @GET("/subreddits/mine/{where}/.json")
    fun fetchUserSubreddits(
        @Path("where") where: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubredditListing>

    @GET("/api/v1/me/trophies/.json")
    fun selfUserTrophies(
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedTrophyList>

    @GET("/api/v1/user/{username}/trophies/.json")
    fun userTrophies(
        @Path("username") username: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedTrophyList>

    @GET("/r/{subreddit}/about/.json")
    fun subreddit(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubreddit>

    @GET("/api/info/.json")
    fun submission(
        @Query("id") submissionId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET("/r/{subreddit}/.json")
    fun fetchSubmissions(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET("/api/info/.json")
    fun comment(
        @Query("id") commentId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedCommentListing>

    @GET("/comments/{submissionId}/.json")
    fun fetchComments(
        @Path("submissionId") submissionId: String,
        @Query("limit") limit: Int? = null,
        @Query("depth") depth: Int? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<List<EnvelopedContributionListing>>

    @GET("/r/{subreddit}/wiki/.json")
    fun wiki(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedWikiPage>

    @GET("/r/{subreddit}/about/rules/.json")
    fun rules(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<SubredditRules>
}