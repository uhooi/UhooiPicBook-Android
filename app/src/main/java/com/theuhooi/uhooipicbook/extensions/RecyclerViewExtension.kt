package com.theuhooi.uhooipicbook.extensions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("observeList")
fun RecyclerView.listObserve(collection: Collection<Any>?) {
    this.adapter?.notifyDataSetChanged()
}
