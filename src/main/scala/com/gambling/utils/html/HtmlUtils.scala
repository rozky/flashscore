package com.gambling.utils.html

object HtmlUtils {
    def removeSpaces(value: String): String = {
        value.replaceAll("[\\xA0]", "").trim
    }
}
