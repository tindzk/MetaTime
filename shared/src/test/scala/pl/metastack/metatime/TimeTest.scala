package pl.metastack.metatime

import org.scalatest.FunSuite
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

class TimeTest extends FunSuite {
  import Implicits._

  test("construct") {
    val time = Time(23, 10, 9, 555)
    assert(time.h == 23)
    assert(time.m == 10)
    assert(time.s == 9)
    assert(time.ms == 555)
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
  
  test("plusMinusTime")
  {
    assertEquals((Time((Hour(30) + Hour(20)).asInstanceOf[Time])).equals(Time(hour=50)), true)
    assertEquals((Time((Minute(20) + Second(40)).asInstanceOf[Time])).equals(Time(hour=10, minute=20)), false)
    assertEquals((Time((Minute(50) - Minute(10)).asInstanceOf[Time])).equals(Time(minute=40)), true)
  }

  test("plusTime") {
    assert(Time(Hour(8) + Hour(2)) == Time(hour = 10))
    assert(Time(Minute(20) + Second(40)) != Time(hour = 10, minute = 20))
    assert(Time(Hour(2) + Minute(60)) == Time(hour = 3))
  }

  test("minusTime") {
    assert(Time(Minute(50) - Minute(10)) == Time(minute = 40))
    assert(Time(Second(1) - Millisecond(100)) == Time(milliseconds = 900))
    assert(Time(Hour(20) - Minute(100)) == Time(hour = 18, minute = 20))
  }

  test("now") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val timeBefore = Time.now()
    val p = Promise[Boolean]()
    scheduler.at(Time(0, 0, 1, 0)) {
      p.success((Time.now() - timeBefore).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  ignore("locale") {
    implicit val locale = Locale.English.America
    //assert(Time(23).format == "11 pm")
  }

  test("fromNow") {
    assert(Minute(120).fromNow.format == "2 hour(s) ago")
  }

  test("millis") {
    assert(Minute(2).milliseconds() == 120000)
    assert(Time(1, 0, 0, 0).milliseconds() == 3600000)
  }
}