package com.kirkbushman.sampleapp.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemSuspendedRedditorBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class SuspendedRedditorModel : ViewBindingEpoxyModelWithHolder<ItemSuspendedRedditorBinding>() {

    @EpoxyAttribute
    lateinit var redditorNameText: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_suspended_redditor
    }

    override fun ItemSuspendedRedditorBinding.bind() {

        redditorName.text = redditorNameText
    }

    override fun ItemSuspendedRedditorBinding.unbind() {

        redditorName.text = null
    }
}
