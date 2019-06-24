package com.kirkbushman.sampleapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.TestApplication
import com.kirkbushman.sampleapp.controllers.InboxController
import com.kirkbushman.sampleapp.doAsync
import kotlinx.android.synthetic.main.fragment_inbox.*

class InboxFragment : Fragment(R.layout.fragment_inbox) {

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

    private val client by lazy { TestApplication.instance.getClient() }
    private val fetcher by lazy {

        when (passedTag) {
            TAG_INBOX -> client?.messages?.inbox()
            TAG_UNREAD -> client?.messages?.unread()
            TAG_MESSAGES -> client?.messages?.messages()
            TAG_SENT -> client?.messages?.sent()
            TAG_MENTIONS -> client?.messages?.mentions()

            else -> null
        }
    }

    private val messages = ArrayList<Message>()
    private val controller by lazy {

        InboxController(object : InboxController.InboxCallback {

            override fun readMessageClick(index: Int) {

                doAsync(doWork = {
                    val message = messages[index]
                    client?.messages?.markAsRead(true, message)
                })
            }

            override fun unreadMessageClick(index: Int) {

                doAsync(doWork = {
                    val message = messages[index]
                    client?.messages?.markAsRead(false, message)
                })
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        list.setController(controller)

        doAsync(doWork = {

            val temp = fetcher?.fetchNext() ?: listOf()
            messages.addAll(temp)
        }, onPost = {

            controller.setMessages(messages)
        })
    }
}