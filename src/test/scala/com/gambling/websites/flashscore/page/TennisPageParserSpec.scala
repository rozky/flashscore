package com.gambling.websites.flashscore.page

import org.scalatest.{Matchers, WordSpec}
import com.gambling.websites.flashscore.domain.{TennisMatchOdds, Player, SetScore}
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.None

class TennisPageParserSpec extends WordSpec with Matchers {

  "parsePlayerName" should {
    "parse singles player name" in {
      val element: Element = tableCell("<span>Larcher de Brito M. (Por)</span>")

      // when
      val name: Seq[Player] = TennisPage.Parser.parsePlayerName(element)

      // then
      name should be(List(new Player("Larcher de Brito", "M")))
    }

    "parse complex singles player name" in {
      val element: Element = tableCell("<span>Ramirez-H.R. (Esp)</span>")

      // when
      val name: Seq[Player] = TennisPage.Parser.parsePlayerName(element)

      // then
      name should be(List(new Player("Ramirez-H", "R")))
    }

    "parse doubles player names" in {
      val element: Element = tableCell("<span>Krajicek M./Pliskova Ka.</span>")

      // when
      val name: Seq[Player] = TennisPage.Parser.parsePlayerName(element)

      // then
      name should be(List(new Player("Krajicek", "M"), new Player("Pliskova", "Ka")))
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

  "parseMatchOdds" should {
    "parse row with odds" in {
      val html =
        """
          | <td class="cell_ad time ">14:00</td>
          | <td class="cell_ab team-home  bold "><span class="padl">Leonardi F. (Ita)</span></td>
          | <td class="cell_ac team-away "><span class="padl">Di Ienno D. (Ita)</span></td>
          | <td class="cell_sa score  bold ">2&nbsp;:&nbsp;0</td>
          | <td rowspan="1" class="cell_oa kx kx o_1 ">1.70</td>
          | <td rowspan="1" class="cell_oc kx last kx o_2 last ">2.20</td>
        """.stripMargin

      // when
      val odds: Option[TennisMatchOdds] = TennisPage.Parser.parseMatchOdds(tableRow(html))

      // then
      odds.get should be(new TennisMatchOdds(
        home = List(new Player("Leonardi", "F")),
        away = List(new Player("Di Ienno", "D")),
        homeOdds = 1.7f,
        awayOdds = 2.2f))
    }

    "parse row without odds" in {
      val html =
        """
          | <td class="cell_ad time ">14:00</td>
          | <td class="cell_ab team-home  bold "><span class="padl">Leonardi F. (Ita)</span></td>
          | <td class="cell_ac team-away "><span class="padl">Di Ienno D. (Ita)</span></td>
          | <td class="cell_sa score  bold ">2&nbsp;:&nbsp;0</td>
          | <td rowspan="1" class="cell_oa kx kx no-odds ">-</td>
          | <td rowspan="1" class="cell_oc kx last kx no-odds last ">-</td>
        """.stripMargin

      // when
      val odds: Option[TennisMatchOdds] = TennisPage.Parser.parseMatchOdds(tableRow(html))

      // then
      odds should be(None)
    }
  }

  private def tableCell(content: String): Element = {
    Jsoup.parse(s"<table><tr><td>$content</td></tr></table>").getElementsByTag("td").get(0)
  }

  private def tableRow(content: String): Element = {
    Jsoup.parse(s"<table><tr>$content</tr></table>").getElementsByTag("tr").get(0)
  }
}
