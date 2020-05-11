package com.theuhooi.uhooipicbook.extensions

import android.os.Build
import androidx.core.graphics.ColorUtils
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class IntColorTest : IntColorInterface {

    // region TestCase Life-Cycle Methods

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    // endregion

    // region Test Methods

    // region actionBarColorToStatusBarColor()

    @Test
    fun actionBarColorToStatusBarColor() {
        val testCases = listOf(
            Pair(floatArrayOf(0F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(5F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(6F, 0F, 0F), floatArrayOf(0F, 0F, 0F)),
            Pair(floatArrayOf(7F, 0F, 0F), floatArrayOf(1F, 0F, 0F)),
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
