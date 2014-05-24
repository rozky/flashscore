package com.gambling.websites.tennisexplorer

import com.gambling.websites.tennisexplorer.page.PlayerPage.MatchResult
import com.gambling.websites.tennisexplorer.page.PlayerPage
import org.jsoup.Jsoup

class TennisExplorerApi {
    def getPlayerMatches(name: String): List[MatchResult] = {
        val playerPage = Jsoup.connect("http://www.tennisexplorer.com/player/" + name + "/").get()
        val results: List[MatchResult] = PlayerPage.getAllMatches(playerPage)
//        println(results)
        results
    }
}
