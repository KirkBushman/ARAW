package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Prefs(

    @Json(name = "accept_pms")
    val acceptPms: String,

    @Json(name = "activity_relevant_ads")
    val activityRelevantAds: Boolean,

    @Json(name = "allow_clicktracking")
    val allowClickTracking: Boolean,

    @Json(name = "beta")
    val beta: Boolean,

    @Json(name = "clickgadget")
    val clickGadget: Boolean,

    @Json(name = "collapse_left_bar")
    val collapseLeftBar: Boolean,

    @Json(name = "collapse_read_messages")
    val collapseReadMessages: Boolean,

    @Json(name = "compress")
    val compress: Boolean,

    @Json(name = "default_comment_sort")
    val defaultCommentSort: String,

    @Json(name = "design_beta")
    val designBeta: Boolean,

    @Json(name = "domain_details")
    val domainDetails: Boolean,

    @Json(name = "email_digests")
    val emailDigests: Boolean,

    @Json(name = "email_messages")
    val emailMessages: Boolean,

    @Json(name = "email_unsubscribe_all")
    val emailUnsubscribeAll: Boolean,

    @Json(name = "enable_default_themes")
    val enableDefaultThemes: Boolean,

    @Json(name = "hide_ads")
    val hideAds: Boolean,

    @Json(name = "hide_from_robots")
    val hideFromRobots: Boolean,

    @Json(name = "hide_downs")
    val hideDowns: Boolean,

    @Json(name = "hide_ups")
    val hideUps: Boolean,

    @Json(name = "highlight_controversial")
    val highlightControversial: Boolean,

    @Json(name = "highlight_new_comments")
    val highlightNewComments: Boolean,

    @Json(name = "ignore_suggested_sort")
    val ignoreSuggestedSort: Boolean,

    @Json(name = "label_nsfw")
    val labelNsfw: Boolean,

    @Json(name = "lang")
    val lang: String,

    @Json(name = "legacy_search")
    val legacySearch: Boolean,

    @Json(name = "live_orangereds")
    val liveOrangeReds: Boolean,

    @Json(name = "mark_messages_read")
    val markMessagesRead: Boolean,

    @Json(name = "media")
    val media: String,

    @Json(name = "media_preview")
    val mediaPreview: String,

    @Json(name = "min_comment_score")
    val minCommentScore: Int,

    @Json(name = "min_link_score")
    val minLinkScore: Int,

    @Json(name = "monitor_mentions")
    val monitorMentions: Boolean,

    @Json(name = "newwindow")
    val newWindow: Boolean,

    @Json(name = "nightmode")
    val nightmode: Boolean,

    @Json(name = "no_profanity")
    val noProfanity: Boolean,

    @Json(name = "numsites")
    val numSites: Int,

    @Json(name = "num_comments")
    val numComments: Int,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "private_feeds")
    val privateFeeds: Boolean,

    @Json(name = "profile_opt_out")
    val profileOptOut: Boolean,

    @Json(name = "public_server_seconds")
    val publicServerSeconds: Boolean,

    @Json(name = "public_votes")
    val publicVotes: Boolean,

    @Json(name = "research")
    val research: Boolean,

    @Json(name = "search_include_over_18")
    val searchIncludeOver18: Boolean,

    @Json(name = "show_flair")
    val showFlair: Boolean,

    @Json(name = "show_gold_expiration")
    val showGoldExpiration: Boolean,

    @Json(name = "show_link_flair")
    val showLinkFlair: Boolean,

    @Json(name = "show_snoovatar")
    val showSnoovatar: Boolean,

    @Json(name = "show_stylesheets")
    val showStylesheets: Boolean,

    @Json(name = "show_trending")
    val showTrending: Boolean,

    @Json(name = "show_twitter")
    val showTwitter: Boolean,

    @Json(name = "store_visits")
    val storeVisits: Boolean,

    @Json(name = "third_party_data_personalized_ads")
    val thirdPartyDataPersonalizedAds: Boolean,

    @Json(name = "third_party_site_data_personalized_ads")
    val thirdPartySiteDataPersonalizedAds: Boolean,

    @Json(name = "third_party_site_data_personalized_content")
    val thirdPartySiteDataPersonalizedContent: Boolean,

    @Json(name = "threaded_messages")
    val threadedMessages: Boolean,

    @Json(name = "threaded_modmail")
    val threadedModmail: Boolean,

    @Json(name = "top_karma_subreddits")
    val topKarmaSubreddits: Boolean,

    @Json(name = "use_global_defaults")
    val useGlobalDefaults: Boolean,

    @Json(name = "video_autoplay")
    val videoAutoplay: Boolean

) : Parcelable
