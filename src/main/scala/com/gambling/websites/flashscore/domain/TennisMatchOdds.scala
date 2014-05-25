package com.gambling.websites.flashscore.domain

case class TennisMatchOdds(home: Seq[PlayerName],
                           away: Seq[PlayerName],
                           homeOdds: Float,
                           awayOdds: Float) {

}
