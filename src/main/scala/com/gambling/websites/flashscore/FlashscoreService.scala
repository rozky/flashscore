package com.gambling.websites.flashscore

import java.util.Date
import scala.concurrent.Future
import com.gambling.websites.flashscore.domain.{TennisTournament, TennisMatchScore}

trait FlashscoreService {
    def getTennisMatches(date: Date): Seq[TennisMatchScore]
    def getAtpSinglesTournaments: List[TennisTournament]
    def getWtaSinglesTournaments: List[TennisTournament]
}
