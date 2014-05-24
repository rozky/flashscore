package com.gambling.websites.flashscore.page

import org.openqa.selenium.WebDriver
import com.rozky.common.web.extraction.webdriver.WebDriverUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}
import com.gambling.websites.flashscore.domain.{SetScore, MatchScore, TennisMatch}
import org.jsoup.select.Elements
import java.util
import com.rozky.common.web.extraction.jsoup.JsoupUtils

class TennisPage(implicit driver: WebDriver) {

    if (!TennisPage.url.equals(driver.getCurrentUrl)) {
        driver.navigate().to(TennisPage.url)
        WebDriverUtils.waitForElementWithId("fs")
    }

    private val html: Document = Jsoup.parse(driver.getPageSource)

    def getMatches: Seq[TennisMatch] = {
        import TennisPage.Parser._
        val players: Elements = html.select("#fs tbody tr")
        val iterator: util.Iterator[Element] = players.iterator()

        var result: List[TennisMatch] = List[TennisMatch]()
        while(iterator.hasNext) {
            val firstRow: Element = iterator.next()
            if (firstRow.getElementsByClass("blank-line").size() == 0) {
                if (iterator.hasNext) {
                    val secondRow: Element = iterator.next()


                    result = result :+ new TennisMatch(
                        homeName = parsePlayerName(firstRow.getElementsByClass("team-home").get(0)),
                        awayName = parsePlayerName(secondRow.getElementsByClass("team-away").get(0)),
                        score = null,
                        status = firstRow.getElementsByClass("timer").get(0).text(),
                        startTime = firstRow.getElementsByClass("time").get(0).text()
                    )
                } else {
                    // todo - log problem
                }    
            }
        }

        result
    }




    private def parseScore(el: Element): Seq[String] = {
        el.getElementsByClass("score-home")  // score-away
        el.getElementsByClass("part-bottom") // part-top x 6
        // tie-break <sub>7</sub>
        null
    }



}

object TennisPage {
    val url = "http://www.flashscore.com/tennis/"
    object Parser {

        // Larcher de Brito M. (Por)
        // Pliskova Ka. (Cze)
        def parsePlayerName(el: Element): String = {
            el.text()
        }

        def parseMatchScore(home: Element, away: Element): MatchScore = {
            val homeScores: Elements = home.getElementsByClass("part-top")
            val awayScores: Elements = away.getElementsByClass("part-bottom")

            new MatchScore(
                sets = List.range(0, homeScores.size()).map(i => parseSetScore(homeScores.get(i), awayScores.get(i))),
                homeWonSets = Integer.valueOf(home.getElementsByClass("score-home").get(0).text()),
                awayWonSets = Integer.valueOf(home.getElementsByClass("score-away").get(0).text()))
        }

        def parseSetScore(home: Element, away: Element): SetScore = {
            new SetScore(
                home = JsoupUtils.toInt(home),
                away = JsoupUtils.toInt(away))
        }
    }
}
