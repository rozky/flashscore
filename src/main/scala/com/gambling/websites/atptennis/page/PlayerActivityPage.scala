package com.gambling.websites.atptennis.page

import org.jsoup.nodes.Element
import scala.collection.JavaConversions._

// parser page fragment
object PlayerActivityPage {

    def parseTournamentTable(tournamentTable: Element): TournamentResult = {
        val matches = tournamentTable.select("tbody tr")
            .filterNot(isTableHeaderRow)
            .map(parseMatchResult)

        TournamentResult(info = parserTournamentInformation(tournamentTable), matches = matches.toList)
    }

    def parseMatchResult(row: Element): MatchResult = {
        val cells = row.select("td").toList
        MatchResult(round = cells.get(0).text(),
            player = cells.get(1).text(),
            ranking = cells.get(2).text(),
            score = cells.get(3).text())
    }

    def parserTournamentInformation(tournamentTable: Element): TournamentInformation = {
        val information = tournamentTable.select(".bioPlayActivityInfo").first().text().split(";")
        val nameAndLocation = information(0).split(",")

        TournamentInformation(name = nameAndLocation(0).replaceAll("[\\xA0]", ""),
            location = nameAndLocation(1).replaceAll("[\\xA0]", ""),
            start = information(1).replaceAll("[\\xA0]", ""),
            points = information(2).replaceAll("[\\xA0]", ""),
            surface = information(3).replaceAll("[\\xA0]", ""),
            playerCount = information(4).replaceAll("[\\xA0]|Draw:", ""))
    }

    def isTournamentFragment(table: Element): Boolean = table.select(".bioPlayActivityInfo").size() > 0

    def isTableHeaderRow(row: Element): Boolean = row.hasClass("bioTableHead")

    case class TournamentResult(info: TournamentInformation,
                                matches: List[MatchResult])

    case class TournamentInformation(name: String,
                                     location: String,
                                     start: String,
                                     points: String,
                                     surface: String,
                                     playerCount: String)

    case class MatchResult(player: String, round: String, ranking: String, score: String) {
        def isWin: Boolean = score.startsWith("W")
        def isFinal: Boolean = round.equals("F") || round.equals("W")
        def isFinalWin: Boolean = round.equals("W")
        def isSemiFinal: Boolean = round.equals("S")
        def isBey: Boolean = player.equals("Bye")
    }
}
