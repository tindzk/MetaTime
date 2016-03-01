package pl.metastack.metatime

import org.scalatest.FunSuite

class PatternTest extends FunSuite {
  import Implicits._

  ignore("Format") {
    val dateTime = DateTime(2015, 1, 1)
    assert(dateTime.format(Pattern.DefaultDate) == "Thursday, 1 January 2015")
  }

  ignore("Format from now") {
    val offset = (DateTime.now() + 1.day).fromNow
    assert(offset.format == s"1 day(s) from now")
  }

  ignore("Custom pattern") {
    val formatter = Pattern.branch(Seq(
      (0.seconds to 1.minute) -> Pattern.JustNow,
      (1.minute  to 1.hour  ) -> Pattern.MinutesAgo,
      (1.hour    to 1.day   ) -> Pattern.HoursAgo,
      (1.day     to 1.week  ) -> Pattern.DaysAgo
    ), Pattern.DefaultDateTime)

    val now = DateTime.now()
    assert(formatter.format(now) == "just now")
    assert(formatter.format((now - 5.minutes).asInstanceOf[DateTime]) == "5 minute(s) ago")
    assert(formatter.format((now - 60.minutes).asInstanceOf[DateTime]) == "1 hour(s) ago")

    val twoWeeks = (now - 2.weeks).asInstanceOf[DateTime]
    assert(formatter.format(twoWeeks) == Pattern.DefaultDateTime.format(twoWeeks))
  }
}
