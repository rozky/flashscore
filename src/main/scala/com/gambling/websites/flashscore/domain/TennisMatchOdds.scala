package com.gambling.websites.flashscore.domain

case class TennisMatchOdds(home: Seq[Player],
                           away: Seq[Player],
                           homeOdds: Float,
                           awayOdds: Float) {

}
