package com.theuhooi.uhooipicbook.util

object Converter {

    @JvmStatic
    fun convertNewLine(text: String): String {
        return text.replace("\\n", "\n")
    }
}
