package com.kirkbushman.sampleapp.controllers.base

import com.airbnb.epoxy.EpoxyController
import com.kirkbushman.sampleapp.models.empty

abstract class BaseController2<T> : BaseController<T, BaseCallback>(null)

abstract class BaseController<T, C : BaseCallback>(private val callback: C?) : EpoxyController() {

    protected val items = ArrayList<T>()

    abstract fun itemModel(index: Int, it: T, callback: C?)

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        requestModelBuild()
    }

    override fun buildModels() {
        if (items.isEmpty()) {
            empty {
                id("empty_items")
            }
        }

        items.forEachIndexed { index, it ->
            itemModel(index, it, callback)
        }
    }
}
