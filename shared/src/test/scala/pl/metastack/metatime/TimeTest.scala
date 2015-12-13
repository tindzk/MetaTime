package pl.metastack.metatime

import org.scalatest.FunSuite

class TimeTest extends FunSuite {
  import Implicits._

  ignore("construct") {
    assert(Time(23, 10) == ???)
  }

  test("implicits") {
    assert(Hour(23) == 23.hour)
  }

  ignore("hour") {
    assert(Hour(25).days == Day(1 + 1.0 / 24))
  }

  test("operators") {
    assert(Time(23, 10) > Time(22, 0))
    assert(Time(23, 10) > Hour(22))
    assert(Hour(2) > Minute(80))
  }

  test("plusTime") {
    assert((Time(Hour(30) + Hour(20))) == Time(hour = 50))
    assert((Time(Minute(20) + Second(40))) != Time(hour = 10, minute = 20))
    assert((Time(Hour(2) + Minute(60))) == Time(hour = 3))
  }

  test("minusTime") {
    assert((Time(Minute(50) - Minute(10))) == Time(minute = 40))
    assert((Time(Second(1) - Millisecond(100))) == Time(milliseconds = 900))
    assert((Time(Hour(50) - Minute(100))) == Time(hour = 48, minute = 20))
  }

  ignore("now") {
    val timeBefore = Time.now()
    // Add Delay
    val timeAfter = Time.now()
    assert((timeBefore - timeAfter) >= Time(milliseconds=1000))
  }

  ignore("locale") {
    implicit val locale = Locale.English.America
    assert(Time(23).format == "11 pm")
  }

  ignore("fromNow") {
    assert(Minute(5).fromNow == ???)
  }
}
