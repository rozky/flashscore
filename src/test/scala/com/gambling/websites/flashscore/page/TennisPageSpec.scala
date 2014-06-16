package com.gambling.websites.flashscore.page

import org.scalatest.{Matchers, WordSpec}
import com.rozky.common.web.extraction.phantomjs.PhantomJsExecutor
import com.gambling.websites.flashscore.domain.{TennisMatchOdds, TennisMatchScore}

class TennisPageSpec extends WordSpec with Matchers {

    "getMatches" should {
        "extract all matches" in {
            val matches: Seq[TennisMatchScore] = PhantomJsExecutor.execute(implicit driver =>  new TennisPage().getMatches)

            matches.foreach(m => println(m))
        }
    }

    "getMatchOdds" should {
        "extract matches oads" in {
            val matches: Seq[TennisMatchOdds] = PhantomJsExecutor.execute(implicit driver =>  new TennisPage().getMatchOdds)

            matches.foreach(m => println(m))
        }
    }
}
