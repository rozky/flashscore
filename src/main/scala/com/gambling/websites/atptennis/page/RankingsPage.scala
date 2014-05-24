package com.gambling.websites.atptennis.page

import org.jsoup.nodes.{Document, Element}
import scala.collection.JavaConversions._
import com.gambling.utils.html.HtmlUtils
import HtmlUtils.removeSpaces

// parser page fragment
object RankingsPage {
    val URI = "/Rankings/Singles.aspx"

    def parseRankings(page: Element): List[PlayerRanking] = {
        val players = page.select(".rankingsContent tbody tr")
            .filterNot(_.hasClass("bioTableHead"))
            .map(parsePlayerRow).toList

        players.foreach(println(_))
        players
    }

    def parsePlayerRow(html: Element): PlayerRanking = {
        val cells = html.select("td").toList

        new PlayerRanking(
            removeSpaces(cells(0).select("a").text()),
            removeSpaces(cells(0).select(".rank").text()),
            removeSpaces(cells(1).text()))
    }

    case class PlayerRanking(name: String, rank: String, points: String)
}
