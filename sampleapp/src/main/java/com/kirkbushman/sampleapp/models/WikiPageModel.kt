package com.kirkbushman.sampleapp.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_trophy)
abstract class WikiPageModel : EpoxyModelWithHolder<WikiPageHolder>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute(DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun bind(holder: WikiPageHolder) {

        holder.name.text = name
        holder.name.setOnClickListener(listener)
    }
}

class WikiPageHolder : KotlinHolder() {

    val name by bind<TextView>(R.id.name)
}
