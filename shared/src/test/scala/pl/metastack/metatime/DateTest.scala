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
}
