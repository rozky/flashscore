package com.gambling.websites

import java.util.Calendar
import com.gambling.websites.atptennis.page.PlayerActivityPage.MatchResult

class DataGeneratorTest extends BaseTest {

    it should "generate calendar" ignore {
        List.range(1,8).foreach(day => {
            List.range(1, 54).foreach(week => println(s"$day,$week,1"))
        })

        Calendar.getInstance()

        // list(days) -> calendar
        // calendar -> List(weeks), List(days)



        // calendar.getDays
        // sortedHashMap



    }


    it should "print out last month days" ignore {
        val calendar: Calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.WEEK_OF_YEAR, 1)
        calendar.set(Calendar.YEAR, 2013)

        for(i <- 0 until 28) {
            println(calendar.getTime + " - " + calendar.get(Calendar.WEEK_OF_YEAR) + " - " + (calendar.get(Calendar.DAY_OF_WEEK)))
            calendar.add(Calendar.DATE, 1)
        }
    }

    it should "merge 2 lists" in {
        val aList: List[Int] = List(1,2,3,4)
        val bList: List[Int] = List(5,5,5,5)

        println(aList.zip(bList))
    }


    case class CalendarHeapMap(matchResults: List[MatchResult]) {

    }
}
