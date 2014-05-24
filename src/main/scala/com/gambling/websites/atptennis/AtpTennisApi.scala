package com.gambling.websites.atptennis

import org.jsoup.Jsoup
import AtpTennisApi.BASE_URL
import scala.collection.JavaConversions._
import com.gambling.websites.atptennis.page.{RankingsPage, PlayerActivityPage}
import com.gambling.websites.atptennis.page.RankingsPage.PlayerRanking
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import com.gambling.websites.atptennis.page.PlayerActivityPage.MatchResult

class AtpTennisApi {

    def getPlayerMatches(name: String): List[MatchResult] = {
        val playerActivitiesPage = Jsoup.connect(s"$BASE_URL/Tennis/Players/Top-Players/$name.aspx?t=pa").get()
        val tournaments = playerActivitiesPage
            .select(".commonProfileContainer")
            .filter(PlayerActivityPage.isTournamentFragment)

        tournaments.map(PlayerActivityPage.parseTournamentTable).map(t => {
            t.matches.filterNot(_.isBey).reverse
        }).flatten.toList
    }

    def getPlayerMatches() {
        val playerName = "Rafael-Nadal"
        //        val playerName = "Roger-Federer"
        //        val playerName = "Novak-Djokovic"
        //        val playerName = "John-Isner"
        //        val playerName = "Ryan-Harrison"
        val playerActivitiesPage = Jsoup.connect(s"$BASE_URL/Tennis/Players/Top-Players/$playerName.aspx?t=pa").get()
        val tournaments = playerActivitiesPage
            .select(".commonProfileContainer")
            .filter(PlayerActivityPage.isTournamentFragment)

        var count = 0;
        tournaments.map(PlayerActivityPage.parseTournamentTable).map(t => {
            val string: String = t.matches.map(m => {
                if (!m.isBey) {
                    count += 1;
                }
                if (m.isWin) {
                    if (m.isFinalWin) {
                        "WW"
                    } else {
                        "W"
                    }
                } else {
                    "L"
                }
            }).mkString("-")

            t.info.points + "-" + {string}
        }).foreach(e => println(e + ",1"))

        println("count = " + count)
    }

    def getSingleRankings: List[PlayerRanking] = {
        val rankPage = Jsoup.connect(s"${BASE_URL}${RankingsPage.URI}").get()
        RankingsPage.parseRankings(rankPage)
    }
}

object AtpTennisApi {
    val BASE_URL = "http://www.atpworldtour.com"
}

