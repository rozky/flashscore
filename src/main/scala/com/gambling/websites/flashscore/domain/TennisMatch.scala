package com.gambling.websites.flashscore.domain

import scala.beans.BeanProperty

// has player retired ?
// number of sets
// SinglesTennisMatch
// DoublesTennisMatch
case class TennisMatch(@BeanProperty home: Seq[PlayerName],
                       @BeanProperty away: Seq[PlayerName],
                       @BeanProperty score: MatchScore,
                       @BeanProperty status: String,
                       @BeanProperty startTime: String) {

//    def isDouble: Boolean = home.contains("/") && away.contains("/")
    def isFinished: Boolean = "Finished".equals(status)
    def isLive: Boolean = false
    def isRetired: Boolean = "Finished (retired)".equals(status)
    def isInterrupted: Boolean = "Interrupted".equals(status)
}

sealed case class MatchScore(@BeanProperty sets: List[SetScore],
                             @BeanProperty homeWonSets: Int,
                             @BeanProperty awayWonSets: Int)

//TieBreakSetScore
// TieBreakSet
// RegularSet
sealed case class SetScore(@BeanProperty home: Int,
                           @BeanProperty away: Int,
                           @BeanProperty homeTieBreak: Option[Int] = None,
                           @BeanProperty awayTieBreak: Option[Int] = None) {
    def isHomeWinner: Boolean = false
    def isTieBreakPlayed: Boolean = homeTieBreak.isDefined
    def isFinished: Boolean = true
}

sealed case class PlayerName(lastName: String, firstName: String)


