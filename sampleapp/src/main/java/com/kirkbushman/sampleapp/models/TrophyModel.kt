package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_trophy)
abstract class TrophyModel : EpoxyModelWithHolder<TrophyHolder>() {

    @EpoxyAttribute
    lateinit var name: String

    override fun bind(holder: TrophyHolder) {

        holder.name.text = name
    }
}

class TrophyHolder : KotlinHolder() {

    val name by bind<TextView>(R.id.name)
}