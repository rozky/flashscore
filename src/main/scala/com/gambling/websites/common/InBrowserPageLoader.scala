package com.gambling.websites.common

import org.openqa.selenium.{By, WebDriver}
import scala.concurrent.Future
import org.openqa.selenium.remote.{RemoteWebDriver, DesiredCapabilities}
import java.net.URL
import com.gambling.websites.common.webdriver.WebDriverUtils
import scala.concurrent.ExecutionContext.Implicits.global

class InBrowserPageLoader {

    /**
     * Load a page defined by the given url and waits for a element matching supplied waitForSelector to be visible.
     *
     * @param url the page url
     * @param waitForSelector the css selector for a required element to be visible
     * @param contentSelector the css selector for a content that should be returned
     * @return the future containing inner html of the element matching contentSelector
     */
    def getContent(url: String, waitForSelector: String, contentSelector: String): Future[String] = {
        Future {
            implicit val driver = phantomJsDriver() // todo - maybe I can re-use that browser for subsequent calls

            try {
                driver.navigate().to(url)

                WebDriverUtils.waitForCssSelector(waitForSelector)

                driver.findElement(By.cssSelector(contentSelector)).getAttribute("innerHTML")
            } finally {
                driver.quit()
            }
        }
    }

    // todo: WebDriverPool
    private def phantomJsDriver(): WebDriver = {
        val capabilities = new DesiredCapabilities()
        capabilities.setBrowserName("phantomjs")
        new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"), capabilities)
    }
}
