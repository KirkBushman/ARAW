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
import com.kirkbushman.araw.http.MoreChildrenResponse
import com.kirkbushman.araw.http.Reply
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.SubredditRules
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
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

    @FormUrlEncoded
    @POST("/api/read_message")
    fun readMessage(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unread_message")
    fun unreadMessage(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/user/{username}/about/.json")
    fun user(
        @Path("username") username: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedRedditor>

    @GET("/user/{username}/.json")
    fun fetchUserOverview(
        @Path("username") username: String,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
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
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
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

    @FormUrlEncoded
    @POST("/api/subscribe")
    fun subscribe(
        @Field("action") action: String,
        @Field("sr") subredditIds: String? = null,
        @Field("sr_name") subredditNames: String? = null,
        @Field("skip_initial_defaults") skipInitialDefaults: Boolean? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/api/info/.json")
    fun submission(
        @Query("id") submissionId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET("/{sorting}/.json")
    fun fetchSubmissions(
        @Path("sorting") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET("/r/{subreddit}/{sorting}/.json")
    fun fetchSubmissions(
        @Path("subreddit") subreddit: String,
        @Path("sorting") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET("/r/{subreddit}/search/.json")
    fun fetchSubmissionsSearch(
        @Path("subreddit") subreddit: String,
        @Query("q") query: String,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @FormUrlEncoded
    @POST("/api/submit")
    fun submit(
        @Field("api_type") apiType: String = "json",
        @Field("extension") extension: String = "json",
        @Field("sr") subreddit: String,
        @Field("title") title: String,
        @Field("kind") kind: String,
        @Field("text") text: String? = null,
        @Field("url") url: String? = null,
        @Field("resubmit") resubmit: Boolean = false,
        @Field("sendreplies") sendReplies: Boolean,
        @Field("nsfw") isNsfw: Boolean,
        @Field("spoiler") isSpoiler: Boolean,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/api/info/.json")
    fun comment(
        @Query("id") commentId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedCommentListing>

    @GET("/api/morechildren")
    fun moreChildren(
        @Query("api_type") apiType: String = "json",
        @Query("children") children: String,
        @Query("link_id") linkId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<MoreChildrenResponse>

    @GET("/comments/{submissionId}/.json")
    fun fetchComments(
        @Path("submissionId") submissionId: String,
        @Query("limit") limit: Int? = null,
        @Query("depth") depth: Int? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<List<EnvelopedContributionListing>>

    @FormUrlEncoded
    @POST("/api/comment")
    fun reply(
        @Field("api_type") apiType: String = "json",
        @Field("thing_id") fullname: String,
        @Field("text") text: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Reply>

    @FormUrlEncoded
    @POST("/api/vote")
    fun vote(
        @Field("id") id: String,
        @Field("dir") dir: Int,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/save")
    fun save(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unsave")
    fun unsave(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

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
