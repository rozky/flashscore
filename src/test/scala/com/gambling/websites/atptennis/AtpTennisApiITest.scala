package com.gambling.websites.atptennis

import org.scalatest.FlatSpec

class AtpTennisApiITest extends FlatSpec {

    it should "get matches played by the player in a selected year" in {
        new AtpTennisApi().getPlayerMatches("Rafael-Nadal")
    }

    it should "get player rankings" ignore {
        new AtpTennisApi().getSingleRankings
    }

}
