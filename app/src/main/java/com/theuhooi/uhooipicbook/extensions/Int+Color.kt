package com.theuhooi.uhooipicbook.extensions

import androidx.core.graphics.ColorUtils

fun Int.actionBarColorToStatusBarColor(): Int {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(this, hsl)
    hsl[0] = if (hsl[0] < 6) 0F else hsl[0] - 6
    hsl[2] = if (hsl[2] < 0.09F) 0F else hsl[2] - 0.09F
    return ColorUtils.HSLToColor(hsl)
}
