package com.theuhooi.uhooipicbook.extensions

import androidx.core.graphics.ColorUtils
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class IntColorTest : IntColorInterface {

    // region Test Methods

    // region actionBarColorToStatusBarColor()

    @Test
    fun actionBarColorToStatusBarColor() {
        val testCases = listOf(
            Pair(floatArrayOf(0F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(5F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(6F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(7F, 0F, 0F), floatArrayOf(0F, 0F, 0F)), // FIXME: expected: `floatArrayOf(1F, 0F, 0F)`
            Pair(floatArrayOf(360F, 0F, 0F), floatArrayOf(354F, 0F, 0F)),
            Pair(floatArrayOf(0F, 1F, 0F), floatArrayOf(0F, 1F, 0F)),
            Pair(floatArrayOf(0F, 0F, 0.08F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(0F, 0F, 0.09F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(0F, 0F, 0.1F), floatArrayOf(0F, 0F, 0.01F)),
            Pair(floatArrayOf(0F, 0F, 1F), floatArrayOf(0F, 0F, 0.91F))
        )

        for ((beforeColor, expected) in testCases) {
            assertEquals(
                ColorUtils.HSLToColor(beforeColor).actionBarColorToStatusBarColor,
                ColorUtils.HSLToColor(expected)
            )
        }
    }

    // endregion

    // endregion

}
