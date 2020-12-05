package com.kirkbushman.sampleapp.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemTrophyBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class TrophyModel : ViewBindingEpoxyModelWithHolder<ItemTrophyBinding>() {

    @EpoxyAttribute
    lateinit var nameText: String

    override fun getDefaultLayout(): Int {
        return R.layout.item_trophy
    }

    override fun ItemTrophyBinding.bind() {

        name.text = nameText
    }

    override fun ItemTrophyBinding.unbind() {

        name.text = null
    }
}
