package com.kirkbushman.sampleapp.controllers

import android.view.View
import com.kirkbushman.araw.models.Message
import com.kirkbushman.sampleapp.controllers.base.BaseCallback
import com.kirkbushman.sampleapp.controllers.base.BaseController
import com.kirkbushman.sampleapp.models.message

class InboxController(callback: InboxCallback) : BaseController<Message, InboxController.InboxCallback>(callback) {

    interface InboxCallback : BaseCallback {

        fun readMessageClick(index: Int)
        fun unreadMessageClick(index: Int)
    }

    override fun itemModel(index: Int, it: Message, callback: InboxCallback?) {

        message {
            id(it.id)
            subjectText(it.subject)
            authorText(it.author ?: "")
            bodyText(it.body)
            readClick(View.OnClickListener { callback?.readMessageClick(index) })
            unreadClick(View.OnClickListener { callback?.unreadMessageClick(index) })
        }
    }
}
