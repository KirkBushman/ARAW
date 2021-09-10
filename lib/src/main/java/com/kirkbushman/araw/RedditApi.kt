package com.kirkbushman.araw

import com.kirkbushman.araw.http.EnvelopedCommentListing
import com.kirkbushman.araw.http.EnvelopedContributionListing
import com.kirkbushman.araw.http.EnvelopedData
import com.kirkbushman.araw.http.EnvelopedMessageListing
import com.kirkbushman.araw.http.EnvelopedMulti
import com.kirkbushman.araw.http.EnvelopedMultiDescription
import com.kirkbushman.araw.http.EnvelopedRedditorData
import com.kirkbushman.araw.http.EnvelopedRedditorDataListing
import com.kirkbushman.araw.http.EnvelopedSubmissionListing
import com.kirkbushman.araw.http.EnvelopedSubredditData
import com.kirkbushman.araw.http.EnvelopedSubredditDataListing
import com.kirkbushman.araw.http.EnvelopedWikiPage
import com.kirkbushman.araw.http.EnvelopedWikiRevisionListing
import com.kirkbushman.araw.http.base.Listing
import com.kirkbushman.araw.models.Flair
import com.kirkbushman.araw.models.FriendList
import com.kirkbushman.araw.models.KarmaList
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.ModeratedList
import com.kirkbushman.araw.models.responses.MoreChildrenResponse
import com.kirkbushman.araw.models.MultiSub
import com.kirkbushman.araw.models.Prefs
import com.kirkbushman.araw.models.Reply
import com.kirkbushman.araw.models.responses.SubmitResponse
import com.kirkbushman.araw.models.SubredditRules
import com.kirkbushman.araw.models.SubredditSearchResult
import com.kirkbushman.araw.models.TrendingSubreddits
import com.kirkbushman.araw.models.TrophyList
import com.kirkbushman.araw.models.UploadContract
import com.kirkbushman.araw.models.UserList
import com.kirkbushman.araw.models.WikiPageList
import com.kirkbushman.araw.utils.Endpoints
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RedditApi {

    // --- Account section: BEGIN ---

    @GET(Endpoints.URL_ME)
    fun me(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<Me>

    @GET(Endpoints.URL_MY_BLOCKED)
    fun myBlocked(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<Any>

    @GET(Endpoints.URL_MY_FRIENDS)
    fun myFriends(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<FriendList>

    @GET(Endpoints.URL_MY_KARMA)
    fun myKarma(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<KarmaList>

    @GET(Endpoints.URL_MY_PREFS)
    fun myPrefs(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<Prefs>

    @GET(Endpoints.URL_MY_TROPHIES)
    fun myTrophies(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<TrophyList>

    // --- Account section: END ---

    // --- Links and Comments section: BEGIN ---

    @FormUrlEncoded
    @POST(Endpoints.URL_SUBSCRIBE)
    fun subscribe(
        @Field("action") action: String,
        @Field("sr") subredditIds: String? = null,
        @Field("sr_name") subredditNames: String? = null,
        @Field("skip_initial_defaults") skipInitialDefaults: Boolean? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_SUBMIT)
    fun submit(
        @Query("resubmit") resubmit: Boolean? = null,
        @Query("raw_json") rawJson: Int? = null,
        @Field("api_type") apiType: String = "json",
        @Field("extension") extension: String? = null,
        @Field("sr") subreddit: String,
        @Field("title") title: String,
        @Field("kind") kind: String,
        @Field("text") text: String? = null,
        @Field("richtext_json") richtextJson: String? = null,
        @Field("url") url: String? = null,
        @Field("submit_type") submitType: String? = null,
        @Field("sendreplies") sendReplies: Boolean,
        @Field("nsfw") isNsfw: Boolean? = null,
        @Field("spoiler") isSpoiler: Boolean? = null,
        @Field("original_content") isOriginalContent: Boolean? = null,
        @Field("validate_on_submit") validateOnSubmit: Boolean? = null,
        @Field("show_error_list") showErrorList: Boolean? = null,
        @HeaderMap header: Map<String, String>
    ): Call<SubmitResponse>

    @FormUrlEncoded
    @POST(Endpoints.URL_VOTE)
    fun vote(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @Field("dir") dir: Int,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_REPLY)
    fun reply(
        @Query("raw_json") rawJson: Int? = null,
        @Field("api_type") apiType: String = "json",
        @Field("thing_id") fullname: String,
        @Field("text") text: String,
        @HeaderMap header: Map<String, String>
    ): Call<Reply>

    @FormUrlEncoded
    @POST(Endpoints.URL_DELETE)
    fun delete(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any>

    @FormUrlEncoded
    @POST(Endpoints.URL_MARK_NSFW)
    fun markAsNsfw(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNMARK_NSFW)
    fun unmarknsfw(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_MARK_SPOILER)
    fun markAsSpoiler(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNMARK_SPOILER)
    fun unspoiler(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_SAVE)
    fun save(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNSAVE)
    fun unsave(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_HIDE)
    fun hide(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNHIDE)
    fun unhide(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_LOCK)
    fun lock(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNLOCK)
    fun unlock(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_FRIEND)
    fun friend(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNFRIEND)
    fun unfriend(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @GET(Endpoints.URL_COMMENT)
    fun comment(
        @Query("id") commentId: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedCommentListing>

    @GET(Endpoints.URL_SUBMISSION)
    fun submission(
        @Query("id") submissionId: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @FormUrlEncoded
    @POST(Endpoints.URL_MORECHILDREN)
    fun moreChildren(
        @Field("api_type") apiType: String = "json",
        @Field("children") children: String,
        @Field("limit_children") limitChildren: Boolean? = null,
        @Field("sort") sorting: String? = null,
        @Field("depth") depth: Int? = null,
        @Field("id") id: String? = null,
        @Field("link_id") linkId: String,
        @Field("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<MoreChildrenResponse>

    @GET
    fun trendingSubreddits(
        @Url url: String = "https://www.reddit.com/api/trending_subreddits/.json",
        @Query("raw_json") rawJson: Int? = null
    ): Call<TrendingSubreddits>

    @FormUrlEncoded
    @POST(Endpoints.URL_UPLOAD_ASSET)
    fun obtainUploadContract(
        @Field("filepath") filepath: String,
        @Field("mimetype") mimetype: String,
        @HeaderMap header: Map<String, String>
    ): Call<UploadContract>

    @Multipart
    @POST
    fun uploadMedia(
        @Url uploadUrl: String,
        @Part("acl") acl: RequestBody,
        @Part("key") key: RequestBody,
        @Part("X-Amz-Credential") xAmzCredential: RequestBody,
        @Part("X-Amz-Algorithm") xAmzAlgorithm: RequestBody,
        @Part("X-Amz-Date") xAmzDate: RequestBody,
        @Part("success_action_status") successActionStatus: RequestBody,
        @Part("content-type") contentType: RequestBody,
        @Part("x-amz-storage-class") xAmzStorageClass: RequestBody,
        @Part("x-amz-meta-ext") xAmzMetaExt: RequestBody,
        @Part("policy") policy: RequestBody,
        @Part("X-Amz-Signature") xAmzSignature: RequestBody,
        @Part("x-amz-security-token") xAmzSecurityToken: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    @GET(Endpoints.URL_SUBMISSIONS_FRONTPAGE)
    fun fetchSubmissions(
        @Path("sorting") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_SUBMISSIONS)
    fun fetchSubmissions(
        @Path("subreddit") subreddit: String,
        @Path("sorting") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_COMMENTS)
    fun fetchComments(
        @Path("submissionId") submissionId: String,
        @Query("comment") focusedCommentId: String? = null,
        @Query("context") focusedCommentParentsNum: Int? = null,
        @Query("sort") sorting: String,
        @Query("limit") limit: Long? = null,
        @Query("depth") depth: Int? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<List<EnvelopedContributionListing>>

    // --- Links and Comments section: END ---

    // --- Messages section: BEGIN ---

    @GET(Endpoints.URL_FETCH_MESSAGES)
    fun fetchMessages(
        @Path("where") where: String,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedMessageListing>

    @FormUrlEncoded
    @POST(Endpoints.URL_DEL_MESSAGE)
    fun deleteMessage(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any>

    @FormUrlEncoded
    @POST(Endpoints.URL_READ_ALL_MESSAGES)
    fun readAllMessages(
        @Query("raw_json") rawJson: Int? = null,
        @Field("filter_types") filters: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any>

    @FormUrlEncoded
    @POST(Endpoints.URL_READ_MESSAGE)
    fun readMessage(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    @FormUrlEncoded
    @POST(Endpoints.URL_UNREAD_MESSAGE)
    fun unreadMessage(
        @Query("raw_json") rawJson: Int? = null,
        @Field("id") id: String,
        @HeaderMap header: Map<String, String>
    ): Call<Any?>

    // --- Messages section: END ---

    // --- Multis section: START ---

    @GET(Endpoints.URL_MULTI_SUBS)
    fun fetchMultiSubmissions(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Path("sorting") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_MULTI)
    fun multi(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedMulti>

    @GET(Endpoints.URL_MULTIS_MINE)
    fun myMultis(
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<List<EnvelopedMulti>>

    @GET(Endpoints.URL_MULTIS_REDDITOR)
    fun redditorMultis(
        @Path("username") username: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<List<EnvelopedMulti>>

    @DELETE(Endpoints.URL_MULTI)
    fun deleteMulti(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<ResponseBody>

    @GET(Endpoints.URL_MULTI_DESC)
    fun multiDescription(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedMultiDescription>

    @FormUrlEncoded
    @PUT(Endpoints.URL_MULTI_DESC)
    fun setMultiDescription(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Field("model") model: String,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedMultiDescription>

    @GET(Endpoints.URL_MULTI_SUB)
    fun multiSubreddit(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Path("subname") subname: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<MultiSub>

    @FormUrlEncoded
    @PUT(Endpoints.URL_MULTI_SUB)
    fun addSubredditToMulti(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Path("subname") subname: String,
        @Query("raw_json") rawJson: Int? = null,
        @Field("model") model: String,
        @HeaderMap header: Map<String, String>
    ): Call<MultiSub>

    @DELETE(Endpoints.URL_MULTI_SUB)
    fun removeSubredditToMulti(
        @Path("username") username: String,
        @Path("multiname") multiname: String,
        @Path("subname") subname: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<ResponseBody>

    // --- Multis section: END ---

    // --- Subreddits section: BEGIN ---

    @GET(Endpoints.URL_SUBREDDIT)
    fun subreddit(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubredditData>

    @GET(Endpoints.URL_SUBREDDITS)
    fun subreddits(
        @Query("id") subredditIds: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubredditDataListing>

    @GET(Endpoints.URL_SUBREDDIT_INFO)
    fun subredditInfo(
        @Path("subreddit") subreddit: String,
        @Path("where") where: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<UserList>

    @GET(Endpoints.URL_SUBREDDIT_RULES)
    fun rules(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<SubredditRules>

    @GET(Endpoints.URL_SUBREDDIT_FLAIRS)
    fun subredditFlairs(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<List<Flair>>

    @GET(Endpoints.URL_REDDITOR_SUBREDDITS)
    fun fetchRedditorSubreddits(
        @Path("where") where: String,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubredditDataListing>

    @FormUrlEncoded
    @POST("/api/search_subreddits")
    fun searchSubreddits(
        @Field("query") query: String,
        @Field("exact") exact: Boolean? = null,
        @Field("include_over_18") includeOver18: Boolean? = null,
        @Field("include_unadvertisable") includeUnadvertisable: Boolean? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<SubredditSearchResult>

    // --- Subreddits section: END ---

    // --- Redditor sections: BEGIN ---

    @GET(Endpoints.URL_REDDITOR)
    fun redditor(
        @Path("username") username: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedRedditorData>

    @GET(Endpoints.URL_REDDITOR_OVERVIEW)
    fun fetchRedditorOverview(
        @Path("username") username: String,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedContributionListing>

    @GET(Endpoints.URL_REDDITOR_WHERE)
    fun fetchRedditorInfo(
        @Path("username") username: String,
        @Path("where") where: String,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedContributionListing>

    @GET(Endpoints.URL_REDDITOR_MODERATED)
    fun redditorModeratedSubreddits(
        @Path("username") username: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<ModeratedList>

    @GET(Endpoints.URL_REDDITOR_TROPHIES)
    fun redditorTrophies(
        @Path("username") username: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<TrophyList>

    // --- Redditor sections: END ---

    // --- Wiki section: BEGIN ---

    @GET(Endpoints.URL_WIKI)
    fun wiki(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedWikiPage>

    @GET(Endpoints.URL_WIKI_PAGE)
    fun wikiPage(
        @Path("subreddit") subreddit: String,
        @Path("page") page: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedWikiPage>

    @GET(Endpoints.URL_WIKI_PAGES)
    fun wikiPages(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<WikiPageList>

    @GET(Endpoints.URL_WIKI_REVISION)
    fun wikiRevision(
        @Path("subreddit") subreddit: String,
        @Path("page") page: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedWikiRevisionListing>

    @GET(Endpoints.URL_WIKI_REVISIONS)
    fun wikiRevisions(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedWikiRevisionListing>

    // --- Wiki section: END ---

    // --- Search section: START ---

    @GET(Endpoints.URL_SEARCH)
    fun search(
        @Query("type") type: String? = null,
        @Query("sort") sorting: String? = null,
        @Query("t") timePeriod: String? = null,
        @Query("show") show: Boolean? = null,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<List<Listing<EnvelopedData>>>

    @GET(Endpoints.URL_SEARCH)
    fun fetchFrontpageSubmissionsSearch(
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("restrict_sr") restrictToSubreddit: Boolean? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_SEARCH_SUBREDDIT)
    fun fetchSubmissionsSearch(
        @Path("subreddit") subreddit: String,
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("restrict_sr") restrictToSubreddit: Boolean? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_SEARCH)
    fun fetchSubredditsSearch(
        @Query("type") type: String = "sr",
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubredditDataListing>

    @GET(Endpoints.URL_SEARCH)
    fun fetchSubmissionsSearchGeneral(
        @Query("type") type: String = "link",
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedSubmissionListing>

    @GET(Endpoints.URL_SEARCH)
    fun fetchRedditorSearch(
        @Query("type") type: String = "user",
        @Query("q") query: String,
        @Query("show") show: String? = null,
        @Query("sort") sorting: String,
        @Query("t") timePeriod: String?,
        @Query("limit") limit: Long,
        @Query("count") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int? = null,
        @HeaderMap header: Map<String, String>
    ): Call<EnvelopedRedditorDataListing>

    // --- Search section: END ---
}
