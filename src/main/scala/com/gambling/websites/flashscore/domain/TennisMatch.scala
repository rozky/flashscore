package com.gambling.websites.flashscore.domain

import scala.beans.BeanProperty

// has player retired ?
// number of sets
case class TennisMatch(@BeanProperty homeName: String,
                       @BeanProperty awayName: String,
                       @BeanProperty score: MatchScore,
                       @BeanProperty status: String,
                       @BeanProperty startTime: String
                          ) {

    def isDouble: Boolean = homeName.contains("/") && awayName.contains("/")
    def isFinished: Boolean = "Finished".equals(status)
    def isLive: Boolean = false
    def isInterrupted: Boolean = false
    def isRetired: Boolean = "Finished (retired)".equals(status)
}

sealed case class MatchScore(@BeanProperty sets: List[SetScore],
                             @BeanProperty homeWonSets: Int,
                             @BeanProperty awayWonSets: Int,
                             @BeanProperty bestOf: Int = 3)

sealed case class SetScore(@BeanProperty home: Int,
                           @BeanProperty away: Int,
                           @BeanProperty homeTieBreak: Option[Int] = None,
                           @BeanProperty awayTieBreak: Option[Int] = None) {
    def isHomeWinner: Boolean = false
    def isTieBreakPlayed: Boolean = homeTieBreak.isDefined
    def isFinished: Boolean = true
}


