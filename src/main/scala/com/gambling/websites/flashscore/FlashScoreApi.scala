package com.gambling.websites.flashscore

import java.util.Date
import scala.concurrent.Future
import com.gambling.domain.tennis.{TennisTournament, TennisMatch}

trait FlashScoreApi {
    def getTennisMatches(date: Date): Future[Seq[TennisMatch]]
    def getAtpSinglesTournaments: Future[List[TennisTournament]]
    def getWtaSinglesTournaments: Future[List[TennisTournament]]
}
