package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.message

class InboxController(private val callback: InboxCallback) : EpoxyController() {

    interface InboxCallback {

        fun readMessageClick(index: Int)
        fun unreadMessageClick(index: Int)
    }

    private val messages = ArrayList<Message>()

    fun setMessages(messages: List<Message>) {
        this.messages.clear()
        this.messages.addAll(messages)
        requestModelBuild()
    }

    override fun buildModels() {

        if (messages.isEmpty()) {
            empty {
                id("empty_items")
            }
        }

        messages.forEachIndexed { index, it ->

            message {
                id(it.id)
                subject(it.subject)
                author(it.author ?: "")
                body(it.body)
                readBttn(View.OnClickListener { callback.readMessageClick(index) })
                unreadBttn(View.OnClickListener { callback.unreadMessageClick(index) })
            }
        }
    }
}