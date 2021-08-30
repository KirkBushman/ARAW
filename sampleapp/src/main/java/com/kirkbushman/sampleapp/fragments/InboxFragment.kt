package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.controllers.InboxController
import com.kirkbushman.sampleapp.fragments.base.BaseControllerFragment
import com.kirkbushman.sampleapp.utils.DoAsync
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxFragment : BaseControllerFragment<Message, InboxController.InboxCallback>() {

    companion object {

        private const val PASSED_TAG = "passed_tag_args"

        const val TAG_INBOX = "inbox"
        const val TAG_UNREAD = "unread"
        const val TAG_MESSAGES = "messages"
        const val TAG_SENT = "sent"
        const val TAG_MENTIONS = "mentions"

        fun newInstance(type: String): InboxFragment {

            val fr = InboxFragment()
            val args = Bundle()

            args.putString(PASSED_TAG, type)
            fr.arguments = args

            return fr
        }
    }

    private val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    override val callback = object : InboxController.InboxCallback {

        override fun readMessageClick(index: Int) {

            DoAsync(
                doWork = {
                    val message = items[index]
                    client.messagesClient.markAsRead(true, message)
                }
            )
        }

        override fun unreadMessageClick(index: Int) {

            DoAsync(
                doWork = {
                    val message = items[index]
                    client.messagesClient.markAsRead(false, message)
                }
            )
        }
    }

    override val controller by lazy {

        InboxController(callback)
    }

    override fun fetchItem(client: RedditClient?): Collection<Message>? {

        val fetcher = when (passedTag) {
            TAG_INBOX -> client?.messagesClient?.createOverviewInboxFetcher()
            TAG_UNREAD -> client?.messagesClient?.createUnreadInboxFetcher()
            TAG_MESSAGES -> client?.messagesClient?.createMessagesInboxFetcher()
            TAG_SENT -> client?.messagesClient?.createSentInboxFetcher()
            TAG_MENTIONS -> client?.messagesClient?.createMentionsInboxFetcher()

            else -> null
        }

        return fetcher?.fetchNext()
    }
}
