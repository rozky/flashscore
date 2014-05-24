package com.gambling.websites.flashscore

import com.gambling.websites.flashscore.domain.{TennisMatch, TennisTournament}
import java.util.Date

class DefaultFlashscoreService extends FlashscoreService {

    def getTennisMatches(date: Date): Seq[TennisMatch] = {
        null
    }

    def getAtpSinglesTournaments: List[TennisTournament] = {
        null
    }

    def getWtaSinglesTournaments: List[TennisTournament] = {
        null
    }
}
