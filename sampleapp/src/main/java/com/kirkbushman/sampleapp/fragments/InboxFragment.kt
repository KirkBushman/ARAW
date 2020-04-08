package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import com.airbnb.epoxy.EpoxyRecyclerView
import com.kirkbushman.araw.RedditClient
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.controllers.BaseController
import com.kirkbushman.sampleapp.controllers.InboxController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.fragment_inbox.*

class InboxFragment : BaseControllerFragment<Message, InboxController.InboxCallback>(R.layout.fragment_inbox) {

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

    val passedTag by lazy { arguments?.getString(PASSED_TAG) ?: "" }

    override val recyclerView: EpoxyRecyclerView
        get() = list

    override val callback: InboxController.InboxCallback?
        get() = object : InboxController.InboxCallback {

            override fun readMessageClick(index: Int) {

                doAsync(doWork = {
                    val message = items[index]
                    client?.messagesClient?.markAsRead(true, message)
                })
            }

            override fun unreadMessageClick(index: Int) {

                doAsync(doWork = {
                    val message = items[index]
                    client?.messagesClient?.markAsRead(false, message)
                })
            }
        }

    override val controller: BaseController<Message, InboxController.InboxCallback> = InboxController(callback!!)

    override fun fetchItem(client: RedditClient?): Collection<Message>? {

        val fetcher = when (passedTag) {
            TAG_INBOX -> client?.messagesClient?.inbox()
            TAG_UNREAD -> client?.messagesClient?.unread()
            TAG_MESSAGES -> client?.messagesClient?.messages()
            TAG_SENT -> client?.messagesClient?.sent()
            TAG_MENTIONS -> client?.messagesClient?.mentions()

            else -> null
        }

        return fetcher?.fetchNext()
    }
}
