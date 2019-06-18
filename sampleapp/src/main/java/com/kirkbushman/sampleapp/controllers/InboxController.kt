package com.kirkbushman.sampleapp.controllers

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.models.empty
import com.kirkbushman.sampleapp.models.message

class InboxController : EpoxyController() {

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

        messages.forEach {

            message {
                id(it.id)
                subject(it.subject)
                author(it.author ?: "")
                body(it.body)
            }
        }
    }
}