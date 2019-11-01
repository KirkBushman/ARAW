package com.kirkbushman.araw

import com.kirkbushman.araw.http.EnvelopedCommentListing
import com.kirkbushman.araw.http.EnvelopedContributionListing
import com.kirkbushman.araw.http.EnvelopedData
import com.kirkbushman.araw.http.EnvelopedMessageListing
import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.EnvelopedRedditorListing
import com.kirkbushman.araw.http.EnvelopedSubmissionListing
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.EnvelopedSubredditListing
import com.kirkbushman.araw.http.EnvelopedWikiPage
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.http.listings.WikiRevisionListing
import com.kirkbushman.araw.models.FriendList
import com.kirkbushman.araw.models.KarmaList
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.MoreChildrenResponse
import com.kirkbushman.araw.models.Prefs
import com.kirkbushman.araw.models.Reply
import com.kirkbushman.araw.models.SubredditRules
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.TrendingSubreddits
import com.kirkbushman.araw.models.TrophyList
import com.kirkbushman.araw.models.UserList
import com.kirkbushman.araw.models.WikiPageList
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RedditApi {

    // --- Account section: BEGIN ---

    @GET("/api/v1/me/.json")
    fun me(
        @HeaderMap header: HashMap<String, String>
    ): Call<Me>

    @GET("/api/v1/me/blocked/.json")
    fun myBlocked(
        @HeaderMap header: HashMap<String, String>
    ): Call<Any>

    @GET("/api/v1/me/friends/.json")
    fun myFriends(
        @HeaderMap header: HashMap<String, String>
    ): Call<FriendList>

    @GET("/api/v1/me/karma/.json")
    fun myKarma(
        @HeaderMap header: HashMap<String, String>
    ): Call<KarmaList>

    @GET("/api/v1/me/prefs/.json")
    fun myPrefs(
        @HeaderMap header: HashMap<String, String>
    ): Call<Prefs>

    @GET("/api/v1/me/trophies/.json")
    fun myTrophies(
        @HeaderMap header: HashMap<String, String>
    ): Call<TrophyList>

    // --- Account section: END ---

    // --- Links and Comments section: BEGIN ---

    @FormUrlEncoded
    @POST("/api/comment")
    fun reply(
        @Field("api_type") apiType: String = "json",
        @Field("thing_id") fullname: String,
        @Field("text") text: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Reply>

    @FormUrlEncoded
    @POST("/api/del")
    fun delete(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any>

    @FormUrlEncoded
    @POST("/api/marknsfw")
    fun markAsNsfw(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/spoiler")
    fun markAsSpoiler(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/hide")
    fun hide(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/api/info/.json")
    fun comment(
        @Query("id") commentId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedCommentListing>

    @GET("/api/info/.json")
    fun submission(
        @Query("id") submissionId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>

    @FormUrlEncoded
    @POST("/api/hide")
    fun lock(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/api/morechildren")
    fun moreChildren(
        @Query("api_type") apiType: String = "json",
        @Query("children") children: String,
        @Query("link_id") linkId: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<MoreChildrenResponse>

    @FormUrlEncoded
    @POST("/api/save")
    fun save(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

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

    @GET
    fun trendingSubreddits(
        @Url url: String = "https://www.reddit.com/api/trending_subreddits/.json"
    ): Call<TrendingSubreddits>

    @FormUrlEncoded
    @POST("/api/unhide")
    fun unhide(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unlock")
    fun unlock(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unmarknsfw")
    fun unmarknsfw(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unsave")
    fun unsave(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/unspoiler")
    fun unspoiler(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST("/api/vote")
    fun vote(
        @Field("id") id: String,
        @Field("dir") dir: Int,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

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

    @GET("/comments/{submissionId}/.json")
    fun fetchComments(
        @Path("submissionId") submissionId: String,
        @Query("limit") limit: Int? = null,
        @Query("depth") depth: Int? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<List<EnvelopedContributionListing>>

    // --- Links and Comments section: END ---

    // --- Messages section: BEGIN ---

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
    @POST("/api/del_msg")
    fun deleteMessage(
        @Field("id") id: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any>

    @FormUrlEncoded
    @POST("/api/read_all_messages")
    fun readAllMessages(
        @Field("filter_types") filters: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any>

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

    // --- Messages section: END ---

    // --- Subreddits section: BEGIN ---

    @GET("/r/{subreddit}/about/.json")
    fun subreddit(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubreddit>

    @GET("/r/{subreddit}/about/{where}/.json")
    fun subredditInfo(
        @Path("subreddit") subreddit: String,
        @Path("where") where: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<UserList>

    @GET("/r/{subreddit}/about/rules/.json")
    fun rules(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<SubredditRules>

    @FormUrlEncoded
    @POST("/api/subscribe")
    fun subscribe(
        @Field("action") action: String,
        @Field("sr") subredditIds: String? = null,
        @Field("sr_name") subredditNames: String? = null,
        @Field("skip_initial_defaults") skipInitialDefaults: Boolean? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any?>

    @GET("/subreddits/mine/{where}/.json")
    fun fetchRedditorSubreddits(
        @Path("where") where: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubredditListing>

    @FormUrlEncoded
    @POST("/api/search_subreddits")
    fun searchSubreddits(
        @Field("query") query: String,
        @Field("exact") exact: Boolean? = null,
        @Field("include_over_18") includeOver18: Boolean? = null,
        @Field("include_unadvertisable") includeUnadvertisable: Boolean? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<SubredditSearchResult>

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

    // --- Subreddits section: END ---

    // --- Redditor sections: BEGIN ---

    @GET("/user/{username}/about/.json")
    fun redditor(
        @Path("username") username: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedRedditor>

    @GET("/user/{username}/.json")
    fun fetchRedditorOverview(
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
    fun fetchRedditorInfo(
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

    @GET("/search")
    fun fetchRedditorSearch(
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("type") type: String = "user",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedRedditorListing>

    @GET("/api/v1/user/{username}/trophies/.json")
    fun redditorTrophies(
        @Path("username") username: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<TrophyList>

    // --- Redditor sections: END ---

    // --- Wiki section: BEGIN ---

    @GET("/r/{subreddit}/wiki/.json")
    fun wiki(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedWikiPage>

    @GET("/r/{subreddit}/wiki/pages/.json")
    fun wikiPages(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<WikiPageList>

    @GET("/r/{subreddit}/wiki/revisions/.json")
    fun wikiRevisions(
        @Path("subreddit") subreddit: String,
        @HeaderMap header: HashMap<String, String>
    ): Call<Any>

    // --- Wiki section: END ---

    @GET("/search")
    fun search(
        @Query("type") type: String? = null,
        @Query("sort") sorting: String? = null,
        @Query("t") timePeriod: String? = null,
        @Query("show") show: Boolean? = null,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<List<Listing<EnvelopedData>>>

    @GET("/search")
    fun fetchSubredditsSearch(
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("type") type: String = "sr",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubredditListing>

    @GET("/search")
    fun fetchSubmissionsSearchGeneral(
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("type") type: String = "link",
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @HeaderMap header: HashMap<String, String>
    ): Call<EnvelopedSubmissionListing>
}
