package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.databinding.ItemTrophyBinding
import com.kirkbushman.sampleapp.models.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class WikiPageModel : ViewBindingEpoxyModelWithHolder<ItemTrophyBinding>() {

    @EpoxyAttribute
    lateinit var nameText: String
    @EpoxyAttribute(DoNotHash)
    lateinit var listener: View.OnClickListener

    override fun getDefaultLayout(): Int {
        return R.layout.item_trophy
    }

    override fun ItemTrophyBinding.bind() {

        name.text = nameText
        name.setOnClickListener(listener)
    }

    override fun ItemTrophyBinding.unbind() {

        name.text = null
        name.setOnClickListener(null)
    }
}
