package com.kirkbushman.sampleapp.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemRuleBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class RuleModel : ViewBindingEpoxyModelWithHolder<ItemRuleBinding>() {

    @EpoxyAttribute
    var priority: Int = 0
    @EpoxyAttribute
    lateinit var shortName: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_rule
    }

    override fun ItemRuleBinding.bind() {

        val text = "Rule $priority - $shortName"
        rule.text = text
    }

    override fun ItemRuleBinding.unbind() {

        rule.text = null
    }
}
