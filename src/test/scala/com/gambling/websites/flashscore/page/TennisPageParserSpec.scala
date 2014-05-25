package com.gambling.websites.flashscore.page

import org.scalatest.{Matchers, WordSpec}
import com.rozky.common.web.extraction.phantomjs.PhantomJsExecutor
import com.gambling.websites.flashscore.domain.{PlayerName, SetScore, TennisMatch}
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}
import com.rozky.common.web.extraction.jsoup.JsoupUtils

class TennisPageParserSpec extends WordSpec with Matchers {

    "parsePlayerName" should {
        "parse singles player name" in {
            val element: Element = tableCell("<span>Larcher de Brito M. (Por)</span>")

            // when
            val name: Seq[PlayerName] = TennisPage.Parser.parsePlayerName(element)

            // then
            name should be(List(new PlayerName("Larcher de Brito", "M")))
        }

        "parse doubles player names" in {
            val element: Element = tableCell("<span>Krajicek M./Pliskova Ka.</span>")

            // when
            val name: Seq[PlayerName] = TennisPage.Parser.parsePlayerName(element)

            // then
            name should be(List(new PlayerName("Krajicek", "M"), new PlayerName("Pliskova", "Ka")))
        }
    }

    "parseSetScore" should {
        "parse set score" in {
            val home: Element = tableCell("6")
            val away: Element = tableCell("4")

            // when
            val score: SetScore = TennisPage.Parser.parseSetScore(home, away)

            // then
            score should be(new SetScore(6, 4))
        }

        "parse set score finished by tie-break" in {
            val home: Element = tableCell("6<sup>8</sup>")
            val away: Element = tableCell("7<sup>10</sup>")

            // when
            val score: SetScore = TennisPage.Parser.parseSetScore(home, away)

            // then
            score should be(new SetScore(6, 7, Some(8), Some(10)))
        }
    }

    private def tableCell(content: String): Element = {
        Jsoup.parse(s"<table><tr><td>$content</td></tr></table>").getElementsByTag("td").get(0)
    }
}
