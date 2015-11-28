package pl.metastack.metatime

import minitest.SimpleTestSuite

object DateTest extends SimpleTestSuite with TestIgnored {
  import Implicits._

  testIgnored("construct") {
    val date = Date(2015, 12, 5)
    assertEquals(date, ???)
  }

  test("implicits") {
    assertEquals(Year(2015), 2015.year)
  }

  testIgnored("date") {
    assertEquals(Year(2015).date, Date(2015, 1, 1))
  }

  testIgnored("add") {
    assertEquals(Date(2015) + 1.day, ???)
  }

  testIgnored("until") {
    assertEquals(Year(2015) until Year(2016), ???)
  }

  testIgnored("now") {
    /** Current time */
    assertEquals(DateTime.now(), ???)

    /** Unix timestamp */
    assertEquals(DateTime.now().timestamp, ???)
  }

  testIgnored("format") {
    val dateTime = DateTime(2015, 1, 1)
    assertEquals(dateTime.format, ???)
    assertEquals(dateTime.format("MMM d, yyyy"), ???)

    val dateTimeOffset = DateTime(2015, 1, 1).fromNow
    assertEquals(dateTimeOffset.format, s"${???} days from now")
  }

  testIgnored("Custom formatter") {
    val formatter = Formatter.branch(Seq(
      (0.seconds to 1.minute) -> Formatter.JustNow,
      (1.minute to 1.hour) -> Formatter.Ago(Formatter.Minutes),
      (1.hour to 1.day) -> Formatter.Ago(Formatter.Hours),
      (1.day to 1.week) -> Formatter.Ago(Formatter.Days)),
      Formatter.DateTime("%monthShort% %dayShort%, %yearLong% %hour%:%minute% %amPm%"))

    assertEquals(formatter.format(DateTime.now()), "just now")
  }
}
