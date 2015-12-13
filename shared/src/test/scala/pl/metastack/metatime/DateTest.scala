package pl.metastack.metatime

import org.scalatest.FunSuite

class DateTest extends FunSuite {
  import Implicits._

  ignore("construct") {
    val date = Date(2015, 12, 5)
    assert(date == ???)
  }

  test("implicits") {
    assert(Year(2015) == 2015.year)
  }

  test("dayUnix") {
    assert(Day(1 + 1.0 / 24).unix().value == 90000000)
  }

  ignore("date") {
    assert(Year(2015).date == Date(2015, 1, 1))
  }

  ignore("add") {
    assert(Date(2015) + 1.day == ???)
  }

  ignore("until") {
    assert((Year(2015) until Year(2016)) == ???)
  }

  ignore("now") {
    /** Current time */
    assert(DateTime.now() == ???)

    /** Unix timestamp */
    assert(DateTime.now().timestamp == ???)
  }

  ignore("format") {
    val dateTime = DateTime(2015, 1, 1)
    assert(dateTime.format == ???)
    assert(dateTime.format("MMM d, yyyy") == ???)

    val dateTimeOffset = DateTime(2015, 1, 1).fromNow
    assert(dateTimeOffset.format == s"${???} days from now")
  }

  ignore("Custom formatter") {
    val formatter = Formatter.branch(Seq(
      (0.seconds to 1.minute) -> Formatter.JustNow,
      (1.minute to 1.hour) -> Formatter.Ago(Formatter.Minutes),
      (1.hour to 1.day) -> Formatter.Ago(Formatter.Hours),
      (1.day to 1.week) -> Formatter.Ago(Formatter.Days)),
      Formatter.DateTime(
        "%monthShort% %dayShort%, %yearLong% %hour%:%minute% %amPm%"))

    assert(formatter.format(DateTime.now()) == "just now")
  }
}
