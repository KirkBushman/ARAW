package com.kirkbushman.sampleapp.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemRedditorBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class RedditorModel : ViewBindingEpoxyModelWithHolder<ItemRedditorBinding>() {

    @EpoxyAttribute
    lateinit var redditorNameText: String
    @EpoxyAttribute
    lateinit var redditorCreatedText: String
    @EpoxyAttribute
    lateinit var redditorKarmaText: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_redditor
    }

    override fun ItemRedditorBinding.bind() {

        redditorName.text = redditorNameText
        redditCreated.text = redditorCreatedText
        redditKarma.text = redditorKarmaText
    }

    override fun ItemRedditorBinding.unbind() {

        redditorName.text = null
        redditCreated.text = null
        redditKarma.text = null
    }
}
