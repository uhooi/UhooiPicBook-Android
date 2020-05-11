package com.theuhooi.uhooipicbook.extensions

import androidx.core.graphics.ColorUtils
import kotlin.math.max

interface IntColorInterface {
    val Int.actionBarColorToStatusBarColor: Int
        get() {
            val hsl = FloatArray(3)
            ColorUtils.colorToHSL(this, hsl)
            hsl[0] = max(0F, hsl[0] - 6)
            hsl[2] = max(0F, hsl[2] - 0.09F)
            return ColorUtils.HSLToColor(hsl)
        }
}
