package com.kirkbushman.sampleapp.models

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass
abstract class EmptyModel : EpoxyModelWithHolder<EmptyHolder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.item_empty
    }

    override fun bind(holder: EmptyHolder) = Unit
}

class EmptyHolder : EpoxyHolder() {
    override fun bindView(itemView: View) {}
}
