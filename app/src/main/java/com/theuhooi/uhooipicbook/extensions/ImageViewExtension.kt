package com.theuhooi.uhooipicbook.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("imageUrl")
fun ImageView.load(url: String?) {
    this.load(url)
}
