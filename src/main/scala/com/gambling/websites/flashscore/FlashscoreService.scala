package com.gambling.websites.flashscore

import java.util.Date
import scala.concurrent.Future
import com.gambling.websites.flashscore.domain.{TennisTournament, TennisMatch}

trait FlashscoreService {
    def getTennisMatches(date: Date): Seq[TennisMatch]
    def getAtpSinglesTournaments: List[TennisTournament]
    def getWtaSinglesTournaments: List[TennisTournament]
}
