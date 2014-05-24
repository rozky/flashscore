package com.gambling.websites.common.jsoup

import org.jsoup.nodes.Element

/**
 * Helpers method for JSOUP html parser
 */
object JsoupUtils {

    /**
     * Gets text of the first child matching supplied selector
     * @param parent the parent element
     * @param selector the children css selector
     * @return text of the 1st child element matching css selector
     */
    def firstChildText(parent: Element, selector: String): Option[String] = {
        val children = parent.select(selector)
        if (!children.isEmpty) {
            Some(children.first().text())
        } else {
            None
        }
    }
}
