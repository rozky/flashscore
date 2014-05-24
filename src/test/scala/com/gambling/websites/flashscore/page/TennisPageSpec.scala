package com.gambling.websites.flashscore.page

import org.scalatest.{Matchers, WordSpec, FlatSpec}
import com.rozky.common.web.extraction.phantomjs.PhantomJsExecutor
import com.gambling.websites.flashscore.domain.{SetScore, TennisMatch}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TennisPageSpec extends WordSpec with Matchers {

    "getMatches" should {
        "extract all matches" in {
            val matches: Seq[TennisMatch] = PhantomJsExecutor.execute(implicit driver =>  new TennisPage().getMatches)
        }
    }

    "parseSetScore" should {
        "parse set score" in {
            val home: Document = Jsoup.parse("<td>6</td>")
            val away: Document = Jsoup.parse("<td>6</td>")

            // when
            val score: SetScore = TennisPage.Parser.parseSetScore(home, away)

            // then
            score should be()
        }
    }
}
