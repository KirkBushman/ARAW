package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass
abstract class RedditorModel : EpoxyModelWithHolder<RedditorHolder>() {

    @EpoxyAttribute
    lateinit var redditorName: String
    @EpoxyAttribute
    lateinit var redditorCreated: String
    @EpoxyAttribute
    lateinit var redditorKarma: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_redditor
    }

    override fun bind(holder: RedditorHolder) {

        holder.redditorName.text = redditorName
        holder.redditorCreated.text = redditorCreated
        holder.redditorKarma.text = redditorKarma
    }
}

class RedditorHolder : KotlinHolder() {

    val redditorName by bind<TextView>(R.id.redditor_name)
    val redditorCreated by bind<TextView>(R.id.reddit_created)
    val redditorKarma by bind<TextView>(R.id.reddit_karma)
}
