package com.gambling.websites.flashscore.page

import org.scalatest.{Matchers, WordSpec}
import com.rozky.common.web.extraction.phantomjs.PhantomJsExecutor
import com.gambling.websites.flashscore.domain.{SetScore, TennisMatch}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TennisPageParserSpec extends WordSpec with Matchers {

    "parseSetScore" should {
        "parse set score" in {
            val home: Document = Jsoup.parse("<td>6</td>")
            val away: Document = Jsoup.parse("<td>4</td>")

            // when
            val score: SetScore = TennisPage.Parser.parseSetScore(home, away)

            // then
            score should be(new SetScore(6, 4))
        }

        "parse set score finished by tie-break" in {
            val home: Document = Jsoup.parse("<td>6<sup>8</sup></td>")
            val away: Document = Jsoup.parse("<td>7<sup>10</sup></td>")

            // when
            val score: SetScore = TennisPage.Parser.parseSetScore(home, away)

            // then
            score should be(new SetScore(6, 7, Some(8), Some(10)))
        }
    }
}
