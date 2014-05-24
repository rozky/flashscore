package com.gambling.websites

import com.gambling.websites.atptennis.AtpTennisApi
import com.gambling.websites.tennisexplorer.TennisExplorerApi
import java.text.SimpleDateFormat
import java.util.{Date, Calendar}

class PlayerHeatMap(year: Int) {

    val outputDateFormat = new SimpleDateFormat("yyyy-MM-dd")

    def buildHeatMap {
        init

        val matches = new AtpTennisApi().getPlayerMatches("Rafael-Nadal")
//        val matches = new AtpTennisApi().getPlayerMatches("John-Isner")
        val dates = new TennisExplorerApi().getPlayerMatches("nadal")
//        val dates = new TennisExplorerApi().getPlayerMatches("isner")

        val dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        val calendar: Calendar = Calendar.getInstance()

        matches.zip(dates).foreach(v => {
            var result = 2;
            if (v._1.isWin) {
                result = 1;
            }

            val dateStr = v._2.date + year.toString
            calendar.setTime(dateFormat.parse(dateStr))

            printLine(calendar, result, dateStr + ": " + v._1.player + " (" + v._1.score.replaceAll(",", "") + ")")
        })




    }

    def init {
        val calendar: Calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.WEEK_OF_YEAR, 1)
        calendar.set(Calendar.YEAR, year)

        while (calendar.get(Calendar.YEAR) < year + 1) {
            printLine(calendar, 0, "no game")
            calendar.add(Calendar.DATE, 1)
        }
    }

    private def printLine(calendar: Calendar, result: Int, title: String) {
//        println(dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + "," + calendar.get(Calendar.WEEK_OF_YEAR) + "," + result + "," + title)
//        println(week + "," + dayOfWeek(day) + "," + result + "," + title)
        println(outputDateFormat.format(calendar.getTime) + "," + result + "," + title)
    }

    private def dayOfWeek(day: Int): Int = {
        day match {
            case 1 => 7
            case _ => day - 1
        }
    }
}
