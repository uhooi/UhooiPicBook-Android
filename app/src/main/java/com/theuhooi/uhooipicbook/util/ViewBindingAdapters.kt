package com.theuhooi.uhooipicbook.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("imageUrl")
fun load(imageView: ImageView, imageUrl: String?) {
    imageView.load(imageUrl)
}

@Suppress("UnusedPrivateMember")
@BindingAdapter("observedList")
fun observeList(recyclerView: RecyclerView, observedList: List<Any>?) {
    recyclerView.adapter?.notifyDataSetChanged()
}
