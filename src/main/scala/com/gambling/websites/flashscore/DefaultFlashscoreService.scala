package com.gambling.websites.flashscore

import com.gambling.websites.flashscore.domain.{TennisMatch, TennisTournament}
import java.util.Date
import com.rozky.common.web.extraction.phantomjs.PhantomJsExecutor
import com.gambling.websites.flashscore.page.TennisPage

class DefaultFlashscoreService extends FlashscoreService {

    def getTennisMatches(date: Date): Seq[TennisMatch] = {
        PhantomJsExecutor.execute(implicit driver =>  new TennisPage().getMatches)
    }

    def getAtpSinglesTournaments: List[TennisTournament] = {
        null
    }

    def getWtaSinglesTournaments: List[TennisTournament] = {
        null
    }
}
