package com.kirkbushman.sampleapp.models

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kirkbushman.sampleapp.R

@EpoxyModelClass(layout = R.layout.item_empty)
abstract class EmptyModel : EpoxyModelWithHolder<EmptyHolder>() {

    override fun bind(holder: EmptyHolder) {}
}

class EmptyHolder : KotlinHolder()
