package com.gambling.websites.atptennis.page

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import com.gambling.websites.atptennis.page.PlayerActivityPage.TournamentInformation
import com.gambling.websites.BaseTest

class PlayerActivityPageTest extends BaseTest {

    val tournamentSnippet =
        """
          |<div class="commonProfileContainer">
          |	<p class="bioPlayActivityInfo"><a href="/Tennis/Tournaments/London-Finals.aspx"><strong>ATP World Tour Finals</strong></a/>,&nbsp;Great Britain;&nbsp;04.11.2013;&nbsp;1500;&nbsp;Indoor:&nbsp;Hard;&nbsp;Draw:&nbsp;8
          |	<div class="bioTableWrap bioTableWrapAlt">
          |        <table cellspacing="0" cellpadding="0" class="bioTableAlt stripeMe">
          |            <tbody><tr class="bioTableHead">
          |                <td width="89">Round</td>
          |                <td width="182">Opponent</td>
          |                <td width="96">Ranking</td>
          |                <td width="189">Score</td>
          |                <td width="38">&nbsp;</td>
          |            </tr>
          |                    <tr>
          |                        <td>RR</td>
          |                        <td><a href="/Tennis/Players/Top-Players/David-Ferrer.aspx">David&nbsp;Ferrer</a>&nbsp;(ESP)</td>
          |                        <td>3</td>
          |                        <td>W&nbsp;6-3, 6-2</td>
          |                        <td><a href="#">Stats</a></td>
          |                    </tr>
          |        </tbody></table>
          |    </div>
          |	<p class="bioPlayActivityInfo"><span>This Event Points:&nbsp;1,000,&nbsp;ATP Ranking:&nbsp;1,&nbsp;Prize Money:&nbsp;$1,013,000</span></p>
          |</div>
        """.stripMargin

    it should "parse tournament information " in {
        // when
        val tournamentInformation = PlayerActivityPage.parserTournamentInformation(tournamentContainer)

        // then
        tournamentInformation should be(
            TournamentInformation("ATP World Tour Finals", "Great Britain", "04.11.2013", "1500", "Indoor:Hard", "8"))
    }
    
    private def tournamentContainer : Element = Jsoup.parse(tournamentSnippet).select(".commonProfileContainer").first()

}
