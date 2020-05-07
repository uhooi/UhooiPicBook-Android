package com.theuhooi.uhooipicbook.extensions

import androidx.core.graphics.ColorUtils

fun Int.actionBarColorToStatusBarColor(): Int {
    var hsl = FloatArray(3)
    ColorUtils.colorToHSL(this, hsl)
    hsl.set(0, if (hsl[0] < 6) 0f else hsl[0] - 6)
    hsl.set(2, if (hsl[2] < 0.09f) 0f else hsl[2] - 0.09f)
    return ColorUtils.HSLToColor(hsl)
}
