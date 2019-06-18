package com.kirkbushman.sampleapp.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_rule)
abstract class RuleModel : EpoxyModelWithHolder<RuleHolder>() {

    @EpoxyAttribute
    var priority: Int = 0

    @EpoxyAttribute
    lateinit var shortName: String

    override fun bind(holder: RuleHolder) {

        val text = "Rule $priority - $shortName"
        holder.rule.text = text
    }
}

class RuleHolder : KotlinHolder() {

    val rule by bind<TextView>(R.id.rule)
}