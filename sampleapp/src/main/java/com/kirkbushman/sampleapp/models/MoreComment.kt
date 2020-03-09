package com.kirkbushman.sampleapp.models

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_more_comments)
abstract class MoreCommentModel : EpoxyModelWithHolder<MoreCommentHolder>() {

    @EpoxyAttribute
    lateinit var more: String

    @EpoxyAttribute
    lateinit var moreListener: View.OnClickListener

    override fun bind(holder: MoreCommentHolder) {

        holder.more.text = more
        holder.loadMore.setOnClickListener(moreListener)
    }

    override fun unbind(holder: MoreCommentHolder) {

        holder.loadMore.setOnClickListener(null)
    }
}

class MoreCommentHolder : KotlinHolder() {

    val container by bind<LinearLayout>(R.id.container)
    val more by bind<TextView>(R.id.more)
    val loadMore by bind<Button>(R.id.load_more_button)
}
