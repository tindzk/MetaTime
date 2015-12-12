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
    assertEquals(Hour(25).days, Day(1 + 1 / 24))
    assertEquals(Hour(25).days.truncate, Day(1))
  }

  test("operators") {
    assertEquals(Time(23, 10) > Time(22, 0), true)
    assertEquals(Time(23, 10) > Hour(22), true)
    assertEquals(Hour(2) > Minute(80), true)
  }

  test("plusTime") {
    assertEquals((Time(Hour(30) + Hour(20))).equals(Time(hour=50)), true)
    assertEquals((Time(Minute(20) + Second(40))).equals(Time(hour=10, minute=20)), false)
    assertEquals((Time(Hour(2) + Minute(60))).equals(Time(hour=3)), true)
  }

  test("minusTime") {
    assertEquals((Time(Minute(50) - Minute(10))).equals(Time(minute=40)), true)
    assertEquals((Time(Second(1) - Millisecond(100))).equals(Time(milliseconds=900)), true)
    assertEquals((Time(Hour(50) - Minute(100))).equals(Time(hour=48, minute = 20)), true)
  }

  testIgnored("now") {
    val timeBefore = Time.now()
    //Add Delay
    val timeAfter = Time.now()
    assertEquals((timeBefore - timeAfter) <= Time(milliseconds=1000), true)
  }

  testIgnored("locale") {
    implicit val locale = Locale.English.America
    assertEquals(Time(23).format, "11 pm")
  }

  testIgnored("fromNow") {
    assertEquals(Minute(5).fromNow, ???)
  }
}
