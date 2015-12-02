package pl.metastack.metatime

import minitest.SimpleTestSuite

object TimeTest extends SimpleTestSuite with TestIgnored {
  import Implicits._

  testIgnored("construct") {
    assertEquals(Time(23, 10), ???)
  }

  test("implicits") {
    assertEquals(Hour(23), 23.hour)
  }

  testIgnored("hour") {
    assertEquals(Hour(25).days, Day(1 + 1.0 / 24))
    assertEquals(Hour(25).days.truncate, Day(1))
  }

  testIgnored("operators") {
    assertEquals(Time(23, 10) > Time(22, 0), true)
    assertEquals(Time(23, 10) > Hour(22), true)
    assertEquals(5.hours - 1.minute, 4.hours + 59.minutes)
  }
  
  test("plusMinusTime")
  {
    assertEquals((Time((Hour(30) + Hour(20)).asInstanceOf[Time])).equals(Time( hour=50)), true)
	  assertEquals((Time((Minute(20) + Second(40)).asInstanceOf[Time])).equals(Time( hour=10, minute=20)), false)
    assertEquals((Time((Minute(50) - Minute(10)).asInstanceOf[Time])).equals(Time( minute=40)), true)
  }

  testIgnored("now") {
    assertEquals(Time.now(), ???)
  }

  testIgnored("locale") {
    implicit val locale = Locale.English.America
    assertEquals(Time(23).format, "11 pm")
  }

  testIgnored("fromNow") {
    assertEquals(Minute(5).fromNow, ???)
  }
}
