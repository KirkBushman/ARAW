package com.kirkbushman.sampleapp.local

/*import com.kirkbushman.araw.http.EnvelopedRedditor
import com.kirkbushman.araw.http.EnvelopedSubreddit
import com.kirkbushman.araw.http.base.EnvelopeKind
import com.kirkbushman.araw.models.Me
import com.kirkbushman.araw.models.RedditorSubreddit
import com.kirkbushman.araw.models.Subreddit
import com.kirkbushman.sampleapp.local.RandomUtil.randomBool
import com.kirkbushman.sampleapp.local.RandomUtil.randomInt
import com.kirkbushman.sampleapp.local.RandomUtil.randomLong
import com.kirkbushman.sampleapp.local.RandomUtil.randomString

object TestDataUtil {

    fun getRedditorSubredditTestData(): RedditorSubreddit {

        return RedditorSubreddit(

            fullname = randomString(),
            bannerImg = randomString(),
            bannerImgSize = listOf(256, 256),
            communityIcon = randomString(),
            description = randomString(),
            displayName = randomString(),
            displayNamePrefixed = randomString(),
            hasDefaultBanner = randomBool(),
            hasDefaultIcon = randomBool(),
            headerImg = randomString(),
            headerImgSize = listOf(256, 256),
            iconImg = randomString(),
            iconImgSize = listOf(256, 256),
            isBanned = randomBool(),
            isContributor = randomBool(),
            isModerator = randomBool(),
            isMuted = randomBool(),
            isSubscriber = randomBool(),
            over18 = randomBool(),
            publicDescription = randomString(),
            subredditType = randomString(),
            subscribers = randomInt(),
            title = randomString(),
            url = randomString()
        )
    }

    val meSubredditTestData = RedditorSubreddit(

        fullname = "t5_ddviw",
        bannerImg = "",
        bannerImgSize = null,
        communityIcon = null,
        description = "",
        displayName = "u_User",
        displayNamePrefixed = "u/User",
        hasDefaultBanner = true,
        hasDefaultIcon = true,
        headerImg = null,
        headerImgSize = null,
        iconImg = "https://www.redditstatic.com/avatars/avatar_default_15_DDBD37.png",
        iconImgSize = listOf(256, 256),
        isBanned = false,
        isContributor = false,
        isModerator = true,
        isMuted = false,
        isSubscriber = false,
        over18 = true,
        publicDescription = "",
        subredditType = "user",
        subscribers = 1,
        title = "",
        url = "/user/User/"
    )

    val meTestData = Me(

        id = "jsdfhs",
        fullname = "User",
        coins = 0,
        commentKarma = 1,
        created = 0L,
        createdUtc = 0L,
        hasMail = false,
        hasModMail = false,
        hasVerifiedEmail = true,
        inboxCount = 0,
        isEmployee = false,
        isGold = false,
        isHidingFromRobots = false,
        isMod = true,
        isSponsor = false,
        isSuspended = false,
        linkKarma = 0,
        numFriends = 1,
        subreddit = meSubredditTestData,
        over18 = true,
        verified = true
    )

    fun getSubredditTestData(): EnvelopedSubreddit {

        return EnvelopedSubreddit(
            kind = EnvelopeKind.Subreddit,
            data = Subreddit(

                id = randomString(),
                fullname = randomString(),
                accountsActive = randomInt(),
                accountsActiveIsFuzzed = randomBool(),
                allowImages = randomBool(),
                arePollsAllowed = randomBool(),
                areVideoAllowed = randomBool(),
                areVideogifsAllowed = randomBool(),
                areEmojisEnabled = randomBool(),
                areSpoilersEnabled = randomBool(),
                communityIcon = randomString(),
                created = randomLong(),
                createdUtc = randomLong(),
                description = randomString(),
                descriptionHtml = randomString(),
                displayName = randomString(),
                displayNamePrefixed = randomString(),
                headerImg = randomString(),
                headerImgSize = listOf(100, 100),
                headerTitle = randomString(),
                iconImg = randomString(),
                iconImgSize = listOf(100, 100),
                isBanned = randomBool(),
                isContributor = randomBool(),
                isModerator = randomBool(),
                isMuted = randomBool(),
                isQuarantined = randomBool(),
                isSubredditCrosspostable = randomBool(),
                isSubscriber = randomBool(),
                isWikiEnabled = randomBool(),
                lang = randomString(),
                over18 = randomBool(),
                publicDescription = randomString(),
                publicDescriptionHtml = randomString(),
                subredditType = randomString(),
                subscribers = randomInt(),
                title = randomString(),
                url = randomString()
            )
        )
    }
}

object TestJsonDataUtil {

    fun getRedditorSubredditJsonData(redditorSubreddit: RedditorSubreddit): String {

        return "{" +
            "\"default_set\": true," +
            "\"user_is_contributor\": ${redditorSubreddit.isContributor}," +
            "\"banner_img\": \"${redditorSubreddit.bannerImg}\"," +
            "\"restrict_posting\": true," +
            "\"user_is_banned\": ${redditorSubreddit.isBanned}," +
            "\"free_form_reports\": true," +
            "\"community_icon\": \"${redditorSubreddit.communityIcon}\"," +
            "\"show_media\": true," +
            "\"icon_color\": \"#DDBD37\"," +
            "\"user_is_muted\": ${redditorSubreddit.isMuted}," +
            "\"display_name\": \"${redditorSubreddit.displayName}\"," +
            "\"header_img\": \"${redditorSubreddit.headerImg}\"," +
            "\"title\": \"${redditorSubreddit.title}\"," +
            "\"coins\": 0," +
            "\"previous_names\": []," +
            "\"over_18\": ${redditorSubreddit.over18}," +
            "\"icon_size\": ${redditorSubreddit.iconImgSize}," +
            "\"primary_color\": \"\"," +
            "\"icon_img\": \"${redditorSubreddit.iconImg}\"," +
            "\"description\": \"${redditorSubreddit.description}\"," +
            "\"submit_link_label\": \"\"," +
            "\"header_size\": ${redditorSubreddit.headerImgSize}," +
            "\"restrict_commenting\": false," +
            "\"subscribers\": ${redditorSubreddit.subscribers}," +
            "\"submit_text_label\": \"\"," +
            "\"is_default_icon\": ${redditorSubreddit.hasDefaultIcon}," +
            "\"link_flair_position\": \"\"," +
            "\"display_name_prefixed\": \"${redditorSubreddit.displayNamePrefixed}\"," +
            "\"key_color\": \"\"," +
            "\"name\": \"${redditorSubreddit.fullname}\"," +
            "\"is_default_banner\": ${redditorSubreddit.hasDefaultBanner}," +
            "\"url\": \"${redditorSubreddit.url}\"," +
            "\"quarantine\": false," +
            "\"banner_size\": ${redditorSubreddit.bannerImgSize}," +
            "\"user_is_moderator\": ${redditorSubreddit.isModerator}," +
            "\"public_description\": \"${redditorSubreddit.publicDescription}\"," +
            "\"link_flair_enabled\": false," +
            "\"disable_contributor_requests\": false," +
            "\"subreddit_type\": \"${redditorSubreddit.subredditType}\"," +
            "\"user_is_subscriber\": ${redditorSubreddit.isSubscriber}" +
            "}"
    }

    val meTestData = "" +
        "{" +
        "\"is_employee\": ${TestDataUtil.meTestData.isEmployee}," +
        "\"seen_layout_switch\": true," +
        "\"has_visited_new_profile\": true," +
        "\"pref_no_profanity\": true," +
        "\"has_external_account\": false," +
        "\"pref_geopopular\": \"GLOBAL\"," +
        "\"seen_redesign_modal\": true," +
        "\"pref_show_trending\": true," +
        "\"subreddit\": " +
        "{" +
        "\"default_set\": true," +
        "\"user_is_contributor\": false," +
        "\"banner_img\": \"\"," +
        "\"restrict_posting\": true," +
        "\"user_is_banned\": false," +
        "\"free_form_reports\": true," +
        "\"community_icon\": null," +
        "\"show_media\": true," +
        "\"icon_color\": \"#DDBD37\"," +
        "\"user_is_muted\": false," +
        "\"display_name\": \"u_User\"," +
        "\"header_img\": null," +
        "\"title\": \"\"," +
        "\"coins\": 0," +
        "\"previous_names\": []," +
        "\"over_18\": true," +
        "\"icon_size\": [256, 256]," +
        "\"primary_color\": \"\"," +
        "\"icon_img\": \"https://www.redditstatic.com/avatars/avatar_default_15_DDBD37.png\"," +
        "\"description\": \"\"," +
        "\"submit_link_label\": \"\"," +
        "\"header_size\": null," +
        "\"restrict_commenting\": false," +
        "\"subscribers\": 1," +
        "\"submit_text_label\": \"\"," +
        "\"is_default_icon\": true," +
        "\"link_flair_position\": \"\"," +
        "\"display_name_prefixed\": \"u/User\"," +
        "\"key_color\": \"\"," +
        "\"name\": \"t5_ddviw\"," +
        "\"is_default_banner\": true," +
        "\"url\": \"${TestDataUtil.meTestData.subreddit?.url}\"," +
        "\"quarantine\": false," +
        "\"banner_size\": null," +
        "\"user_is_moderator\": ${TestDataUtil.meTestData.subreddit?.isModerator}," +
        "\"public_description\": \"\"," +
        "\"link_flair_enabled\": false," +
        "\"disable_contributor_requests\": false," +
        "\"subreddit_type\": \"user\"," +
        "\"user_is_subscriber\": false" +
        "}," +
        "\"is_sponsor\": ${TestDataUtil.meTestData.isSponsor}," +
        "\"gold_expiration\": null," +
        "\"has_gold_subscription\": false," +
        "\"num_friends\": ${TestDataUtil.meTestData.numFriends}," +
        "\"features\": {\"mod_service_mute_writes\": true, \"promoted_trend_blanks\": true, \"show_amp_link\": true, \"report_service_handles_report_writes_to_db_for_helpdesk_reports\": true, \"report_service_handles_self_harm_reports\": true, \"report_service_handles_report_writes_to_db_for_modmail_reports\": true, \"chat\": true, \"reports_double_write_to_report_service_for_spam\": true, \"is_email_permission_required\": true, \"reports_double_write_to_report_service_for_modmail_reports\": true, \"mod_awards\": true, \"report_service_handles_report_writes_to_db_for_sendbird_chats\": true, \"econ_wallet_service\": true, \"chat_subreddit\": true, \"awards_on_streams\": true, \"report_service_handles_accept_report\": true, \"mweb_xpromo_modal_listing_click_daily_dismissible_ios\": true, \"reports_double_write_to_report_service_for_som\": true, \"reports_double_write_to_report_service_for_users\": true, \"modlog_copyright_removal\": true, \"report_service_handles_report_writes_to_db_for_users\": true, \"show_nps_survey\": true, \"do_not_track\": true, \"report_service_handles_report_writes_to_db\": true, \"reports_double_write_to_report_service_for_helpdesk_reports\": true, \"report_service_handles_report_writes_to_db_for_spam\": true, \"reports_double_write_to_report_service_for_sendbird_chats\": true, \"mod_service_mute_reads\": true, \"mweb_xpromo_interstitial_comments_ios\": true, \"noreferrer_to_noopener\": true, \"chat_user_settings\": true, \"premium_subscriptions_table\": true, \"reports_double_write_to_report_service\": true, \"mweb_xpromo_interstitial_comments_android\": true, \"report_service_handles_report_writes_to_db_for_awards\": true, \"reports_double_write_to_report_service_for_awards\": true, \"chat_group_rollout\": true, \"resized_styles_images\": true, \"spez_modal\": true, \"mweb_xpromo_modal_listing_click_daily_dismissible_android\": true, \"expensive_coins_package\": true, \"report_service_handles_report_writes_to_db_for_som\": true}," +
        "\"has_android_subscription\": false," +
        "\"verified\": ${TestDataUtil.meTestData.verified}," +
        "\"new_modmail_exists\": true," +
        "\"pref_autoplay\": true," +
        "\"coins\": 0," +
        "\"has_paypal_subscription\": false," +
        "\"has_subscribed_to_premium\": false," +
        "\"id\": \"${TestDataUtil.meTestData.id}\"," +
        "\"has_stripe_subscription\": false," +
        "\"oauth_client_id\": \"asasasasasasas\"," +
        "\"can_create_subreddit\": true," +
        "\"over_18\": ${TestDataUtil.meTestData.over18}," +
        "\"is_gold\": false," +
        "\"is_mod\": true," +
        "\"awarder_karma\": 0," +
        "\"suspension_expiration_utc\": null," +
        "\"has_verified_email\": ${TestDataUtil.meTestData.hasVerifiedEmail}," +
        "\"is_suspended\": false," +
        "\"pref_video_autoplay\": true," +
        "\"in_chat\": true," +
        "\"can_edit_name\": false," +
        "\"in_redesign_beta\": true," +
        "\"icon_img\": \"https://www.redditstatic.com/avatars/avatar_default_15_DDBD37.png\"," +
        "\"has_mod_mail\": false," +
        "\"pref_nightmode\": true," +
        "\"awardee_karma\": 0," +
        "\"hide_from_robots\": false," +
        "\"password_set\": true," +
        "\"link_karma\": ${TestDataUtil.meTestData.linkKarma}," +
        "\"force_password_reset\": false," +
        "\"total_karma\": 0," +
        "\"seen_give_award_tooltip\": false," +
        "\"inbox_count\": ${TestDataUtil.meTestData.inboxCount}," +
        "\"seen_premium_adblock_modal\": false," +
        "\"pref_top_karma_subreddits\": false," +
        "\"has_mail\": false," +
        "\"pref_show_snoovatar\": false," +
        "\"name\": \"${TestDataUtil.meTestData.fullname}\"," +
        "\"pref_clickgadget\": 1," +
        "\"created\": ${TestDataUtil.meTestData.created}," +
        "\"gold_creddits\": 0," +
        "\"created_utc\": ${TestDataUtil.meTestData.createdUtc}," +
        "\"has_ios_subscription\": false," +
        "\"pref_show_twitter\": false," +
        "\"in_beta\": false," +
        "\"comment_karma\": ${TestDataUtil.meTestData.commentKarma}," +
        "\"has_subscribed\": true," +
        "\"linked_identities\": []," +
        "\"seen_subreddit_chat_ftux\": true" +
        "}"

    fun getSubredditJsonData(subreddit: EnvelopedSubreddit): String {
        return "" +
            "{" +
            "\"kind\": \"${subreddit.kind.value}\"," +
            "\"data\": {" +
            "\"user_flair_background_color\": null," +
            "\"submit_text_html\": \"\"," +
            "\"restrict_posting\": true," +
            "\"user_is_banned\": false," +
            "\"free_form_reports\": true," +
            "\"wiki_enabled\": true," +
            "\"user_is_muted\": false," +
            "\"user_can_flair_in_sr\": true," +
            "\"display_name\": \"\"," +
            "\"header_img\": \"\"," +
            "\"title\": \"Apps for Android!\"," +
            "\"allow_galleries\": true," +
            "\"icon_size\": null," +
            "\"primary_color\": \"\"," +
            "\"active_user_count\": 1438," +
            "\"icon_img\": \"\"," +
            "\"display_name_prefixed\": \"\"," +
            "\"accounts_active\": 1438," +
            "\"public_traffic\": false," +
            "\"subscribers\": 230654," +
            "\"user_flair_richtext\": []," +
            "\"name\": \"t5_2reen\"," +
            "\"quarantine\": false," +
            "\"hide_ads\": false," +
            "\"emojis_enabled\": false," +
            "\"advertiser_category\": \"Technology\"," +
            "\"public_description\": \"\"," +
            "\"comment_score_hide_mins\": 0," +
            "\"allow_predictions\": false," +
            "\"user_has_favorited\": false," +
            "\"user_flair_template_id\": null," +
            "\"community_icon\": \"\"," +
            "\"banner_background_image\": \"\"," +
            "\"original_content_tag_enabled\": false," +
            "\"submit_text\": \"\"," +
            "\"spoilers_enabled\": true," +
            "\"header_title\": \"\"," +
            "\"header_size\": [1,1]," +
            "\"user_flair_position\": \"right\"," +
            "\"all_original_content\": false," +
            "\"has_menu_widget\": false," +
            "\"is_enrolled_in_new_modmail\": null," +
            "\"key_color\": \"\"," +
            "\"can_assign_user_flair\": true," +
            "\"created\": 0," +
            "\"wls\": 6," +
            "\"show_media_preview\": true," +
            "\"submission_type\": \"self\"," +
            "\"user_is_subscriber\": true," +
            "\"disable_contributor_requests\": false," +
            "\"allow_videogifs\": false," +
            "\"user_flair_type\": \"text\"," +
            "\"allow_polls\": false," +
            "\"collapse_deleted_comments\": false," +
            "\"emojis_custom_size\": null," +
            "\"public_description_html\": \"\"," +
            "\"allow_videos\": false," +
            "\"is_crosspostable_subreddit\": true," +
            "\"notification_level\": \"low\"," +
            "\"can_assign_link_flair\": true," +
            "\"accounts_active_is_fuzzed\": false," +
            "\"submit_text_label\": \"\"," +
            "\"link_flair_position\": \"left\"," +
            "\"user_sr_flair_enabled\": true," +
            "\"user_flair_enabled_in_sr\": true," +
            "\"allow_discovery\": false," +
            "\"user_sr_theme_enabled\": true," +
            "\"link_flair_enabled\": true," +
            "\"subreddit_type\": \"public\"," +
            "\"suggested_comment_sort\": null," +
            "\"banner_img\": \"\"," +
            "\"user_flair_text\": null," +
            "\"banner_background_color\": \"\"," +
            "\"show_media\": true," +
            "\"id\": \"2reen\"," +
            "\"user_is_moderator\": false," +
            "\"over18\": false," +
            "\"description\": \"\"," +
            "\"submit_link_label\": \"Submit a new Link | Text\"," +
            "\"user_flair_text_color\": null," +
            "\"restrict_commenting\": false," +
            "\"user_flair_css_class\": null," +
            "\"allow_images\": false," +
            "\"lang\": \"en\"," +
            "\"whitelist_status\": \"all_ads\"," +
            "\"url\": \"\"," +
            "\"created_utc\": 0," +
            "\"banner_size\": null," +
            "\"mobile_banner_image\": \"\"," +
            "\"user_is_contributor\": false" +
            "}" +
            "}"
    }

    fun getRedditorJsonData(redditor: EnvelopedRedditor): String {

        return "" +
            "{" +
            "\"kind\": \"${redditor.kind.value}\"," +
            "\"data\": " +
            "{" +
            "\"is_employee\": ${redditor.data.isEmployee}," +
            "\"is_friend\": ${redditor.data.isFriend}," +
            "\"subreddit\": " +

            (
                if (redditor.data.subreddit != null) {
                    getRedditorSubredditJsonData(redditor.data.subreddit!!).plus(",")
                } else {
                    "null, "
                }
                ) +

            "\"awardee_karma\": 0," +
            "\"id\": \"${redditor.data.id}\"," +
            "\"verified\": true," +
            "\"is_gold\": ${redditor.data.isGold}," +
            "\"is_mod\": ${redditor.data.isMod}," +
            "\"awarder_karma\": 0," +
            "\"has_verified_email\": ${redditor.data.hasVerifiedEmail}," +
            "\"icon_img\": \"icon_img\"," +
            "\"hide_from_robots\": ${redditor.data.isHidingFromRobots}," +
            "\"link_karma\": ${redditor.data.linkKarma}," +
            "\"pref_show_snoovatar\": false," +
            "\"total_karma\": 81025," +
            "\"accept_chats\": true," +
            "\"name\": \"${redditor.data.fullname}\"," +
            "\"created\": ${redditor.data.created}," +
            "\"created_utc\": ${redditor.data.createdUtc}," +
            "\"comment_karma\": ${redditor.data.commentKarma}," +
            "\"has_subscribed\": true," +
            "\"accept_pms\": true" +
            "}" +
            "}"
    }
}
*/
