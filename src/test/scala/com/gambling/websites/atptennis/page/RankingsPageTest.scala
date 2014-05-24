package com.gambling.websites.atptennis.page

import com.gambling.websites.BaseTest
import org.jsoup.Jsoup
import com.gambling.websites.atptennis.page.RankingsPage.PlayerRanking

class RankingsPageTest extends BaseTest {

    "parseRankings" should "parse player rankings" in {
        val htmlFragment =
            """
              |<table class="bioTableAlt stripeMe rankingsContent">
              |    <tbody>
              |    <tr class="bioTableHead oddRow">
              |        <td class="first">Rank, Name & Nationality</td>
              |        <td>Points</td>
              |        <td>Week Change</td>
              |        <td class="last">Tourn Played</td>
              |    </tr>
              |    <tr>
              |        <td class="first">
              |            <span class="rank">1</span>
              |            <a href="#">Nadal,&nbsp;Rafael</a>&nbsp;(ESP)</td>
              |        <td><a href="#">13,030</a></td>
              |        <td>0</td>
              |        <td class="last"><a href="#">20</a></td>
              |    </tr>
              |    <tr class="oddRow">
              |        <td class="first">
              |            <span class="rank">2</span>
              |            <a href="#">Djokovic,&nbsp;Novak</a>&nbsp;(SRB)</td>
              |        <td><a href="#">12,260</a></td>
              |        <td>0</td>
              |        <td class="last"><a href="#">18</a></td>
              |    </tr>
              |</table>
            """.stripMargin

        // when
        val rankings = RankingsPage.parseRankings(Jsoup.parse(htmlFragment))

        // then
        rankings should be(List(PlayerRanking("Nadal,Rafael", "1", "13,030"), PlayerRanking("Djokovic,Novak", "2", "12,260")))
    }

    "parsePlayerRow" should "parse player ranking" in {
        // given
        val htmlFragment =
            """
              |<table>
              |<tr>
              |    <td class="first">
              |        <span class="rank">95</span>
              |        <a href="#">Bedene,&nbsp;Aljaz</a>&nbsp;(SLO)</td>
              |    <td><a href="#">573</a></td>
              |    <td>0</td>
              |    <td class="last"><a href="#">23</a></td>
              |</tr>
              |</table>
            """.stripMargin

        // when
        val playerRanking = RankingsPage.parsePlayerRow(Jsoup.parse(htmlFragment))

        // then
        playerRanking should be(new PlayerRanking("Bedene,Aljaz", "95", "573"))
    }
}
