package com.gambling.websites.common.webdriver

import org.openqa.selenium.{WebDriver, By}
import org.scalatest.concurrent.Eventually
import org.scalatest.time.SpanSugar

object WebDriverUtils extends Eventually with SpanSugar {
    private val defaultTimeout = timeout(30 seconds)
    private val defaultInterval = interval(400 milliseconds)
    
    def waitFor(selector: By)(implicit driver: WebDriver) {
        eventually(defaultTimeout, defaultInterval) {
            driver.findElement(selector)
        }
    }

    def waitForCssSelector(cssSelector: String)(implicit driver: WebDriver) {
        val selector = By.cssSelector(cssSelector)
        eventually(defaultTimeout, defaultInterval) {
            driver.findElement(selector)
        }
    }
}
