package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.models.message

class InboxController(callback: InboxCallback) : BaseController<Message, InboxController.InboxCallback>(callback) {

    interface InboxCallback : BaseCallback {

        fun readMessageClick(index: Int)
        fun unreadMessageClick(index: Int)
    }

    override fun itemModel(index: Int, it: Message, callback: InboxCallback?) {

        message {
            id(it.id)
            subject(it.subject)
            author(it.author ?: "")
            body(it.body)
            readBttn(View.OnClickListener { callback?.readMessageClick(index) })
            unreadBttn(View.OnClickListener { callback?.unreadMessageClick(index) })
        }
    }
}
