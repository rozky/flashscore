package com.gambling.websites.tennisexplorer.page

import org.jsoup.nodes.Element
import scala.collection.JavaConversions._

object PlayerPage {

    def getAllMatches(page: Element): List[MatchResult] = {
        page.select("#matches-2013-1-data td.time").map(e => MatchResult(e.text())).toList
    }

    case class MatchResult(date: String)

}
