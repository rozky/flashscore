package com.gambling.websites.tennisexplorer

import com.gambling.websites.BaseTest
import com.gambling.websites.tennisexplorer.page.PlayerPage.MatchResult

class TennisExplorerApiITest extends BaseTest {
    it should "get Nadals matches" in {
        val matches: List[MatchResult] = new TennisExplorerApi().getPlayerMatches("nadal")
        println(matches.size)

    }

}
