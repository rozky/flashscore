package com.gambling.websites.flashscore.page

import org.openqa.selenium.{By, WebDriver}
import com.rozky.common.web.extraction.webdriver.WebDriverUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}
import com.gambling.websites.flashscore.domain._
import org.jsoup.select.Elements
import java.util
import com.rozky.common.web.extraction.jsoup.JsoupUtils
import java.util.regex.{Matcher, Pattern}
import com.gambling.websites.flashscore.domain.TennisMatch
import com.gambling.websites.flashscore.domain.MatchScore
import com.gambling.websites.flashscore.domain.SetScore
import scala.Some
import com.gambling.websites.flashscore.domain.PlayerName

class TennisPage(implicit driver: WebDriver) {

    if (!TennisPage.url.equals(driver.getCurrentUrl)) {
        driver.navigate().to(TennisPage.url)
        WebDriverUtils.waitForElementWithId("fs")
    }

    def getMatches: Seq[TennisMatch] = {
        import TennisPage.Parser._
        
        val pageHtml: Document = getCurrentPageHtml
        val players: Elements = pageHtml.select("#fs tbody tr")
        val iterator: util.Iterator[Element] = players.iterator()

        var result: List[TennisMatch] = List[TennisMatch]()
        while(iterator.hasNext) {
            val firstRow: Element = iterator.next()
            if (firstRow.getElementsByClass("blank-line").size() == 0) {
                if (iterator.hasNext) {
                    val secondRow: Element = iterator.next()

                    result = result :+ new TennisMatch(
                        home = parsePlayerName(firstRow.getElementsByClass("team-home").get(0)),
                        away = parsePlayerName(secondRow.getElementsByClass("team-away").get(0)),
                        score = parseMatchScore(firstRow, secondRow),
                        status = firstRow.getElementsByClass("timer").get(0).text(),
                        startTime = firstRow.getElementsByClass("time").get(0).text()
                    )
                } else {
                    throw new IllegalArgumentException("away team data not found")
                }    
            }
        }

        result
    }

    def getMatchOdds: Seq[TennisMatchOdds] = {
        import TennisPage.Parser._

        val oddsTabContent: Document = getOddsTabContent

        val players: Elements = oddsTabContent.select("#fs tbody tr")
        val iterator: util.Iterator[Element] = players.iterator()

        var result: List[TennisMatchOdds] = List[TennisMatchOdds]()
        while(iterator.hasNext) {
            val row: Element = iterator.next()
            val matchOdds: Option[TennisMatchOdds] = parseMatchOdds(row)
            matchOdds match {
                case Some(odds) => result = result :+ odds
            }
        }

        result
    }

    private def getOddsTabContent: Document = {
        driver.findElement(By.linkText("bet365 Odds")).click()
        WebDriverUtils.waitFor(By.cssSelector(".odds-content"))
        getCurrentPageHtml
    }
    
    private def getCurrentPageHtml: Document = Jsoup.parse(driver.getPageSource)
}

object TennisPage {
    val url = "http://www.flashscore.com/tennis/"
    object Parser {
        private val NAME_PATTERN = Pattern.compile("(.*) (.*)\\..*")
        def parsePlayerName(el: Element): Seq[PlayerName] = {
            el.text().split('/').map(name => {

                val matcher: Matcher = NAME_PATTERN.matcher(name)
                if (matcher.matches()) {
                    new PlayerName(matcher.group(1), matcher.group(2))
                } else {
                    throw new IllegalArgumentException(name + " is not macthing name pattern")
                }
            })
        }

        def parseMatchScore(home: Element, away: Element): MatchScore = {
            val homeScores: Elements = home.getElementsByClass("part-bottom")
            val awayScores: Elements = away.getElementsByClass("part-top")

            new MatchScore(
                sets = List.range(0, homeScores.size()).map(i => parseSetScore(homeScores.get(i), awayScores.get(i))),
                homeWonSets = JsoupUtils.toInt(home.getElementsByClass("score-home").get(0)),
                awayWonSets = JsoupUtils.toInt(away.getElementsByClass("score-away").get(0)))
        }

        def parseSetScore(home: Element, away: Element): SetScore = {
            val homeTieBreakScore: Elements = home.getElementsByTag("sup")

            if (homeTieBreakScore.size() == 0) {
                new SetScore(
                    home = JsoupUtils.toInt(home),
                    away = JsoupUtils.toInt(away))
            } else {
                val awayTieBreakScore: Elements = away.getElementsByTag("sup")

                new SetScore(
                    home = JsoupUtils.toInt(home),
                    away = JsoupUtils.toInt(away),
                    Some(JsoupUtils.toInt(homeTieBreakScore.get(0))),
                    Some(JsoupUtils.toInt(awayTieBreakScore.get(0)))
                )
            }
        }

        def parseMatchOdds(row: Element): Option[TennisMatchOdds] = {
            val homeOdds: Elements = row.getElementsByClass("o_1")
            val awayOdds: Elements = row.getElementsByClass("o_2")
            if (homeOdds.size() == 1 && awayOdds.size() == 1) {
                Some(new TennisMatchOdds(
                    home = parsePlayerName(row.getElementsByClass("team-home").get(0)),
                    away = parsePlayerName(row.getElementsByClass("team-away").get(0)),
                    homeOdds = JsoupUtils.toFloat(homeOdds.get(0)),
                    awayOdds = JsoupUtils.toFloat(awayOdds.get(0)))
                )
            } else {
                None
            }
        }
    }
}
