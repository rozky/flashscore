package com.gambling.domain.tennis

import scala.beans.BeanProperty

// has player retired ?
// number of sets
case class TennisMatch(@BeanProperty homePlayer: String,
                       @BeanProperty awayPlayer: String,
                       @BeanProperty score: MatchScore,
                       @BeanProperty status: String) {

    def isDouble: Boolean = homePlayer.contains("/") && awayPlayer.contains("/")
}

sealed case class MatchScore(@BeanProperty sets: List[SetScore],
                             @BeanProperty bestOf: Int = 3)

sealed case class SetScore(@BeanProperty home: Int,
                           @BeanProperty away: Int,
                           @BeanProperty homeTieBreak: Option[Int] = None,
                           @BeanProperty awayTieBreak: Option[Int] = None) {
    def isHomeWinner: Boolean = false
    def isDecidedByTieBreak: Boolean = homeTieBreak.isDefined
    def isFinished: Boolean = true
}


