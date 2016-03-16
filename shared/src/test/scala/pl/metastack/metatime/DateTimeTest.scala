package pl.metastack.metatime

import org.scalatest.FunSuite
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

class DateTimeTest extends FunSuite {
  test("construct") {
    val dateTime = DateTime(2016, 1, 1, 10, 10, 50, 500)
    assert(dateTime.year == 2016)
    assert(dateTime.month == 1)
    assert(dateTime.day == 1)
    assert(dateTime.h == 10)
    assert(dateTime.m == 10)
    assert(dateTime.s == 50)
    assert(dateTime.ms == 500)
  }

  test("date") {
    assert(Year(2015).dateTime == DateTime(2015, 1, 1, 0, 0, 0))
  }

  test("Add day") {
    assert(
      DateTime(2016, 1, 1, 1, 1, 1, 1) + Day(1) ==
      DateTime(2016, 1, 2, 1, 1, 1, 1))
  }

  test("Add second") {
    val dt = DateTime.now()
    val dtAfter = dt + Second(1)
    assert(dtAfter.unix().value == dt.unix().value + 1000)
  }

  test("now") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val dt = DateTime.now()
    val dtAfter = (dt + Second(5)).asInstanceOf[DateTime]
    val p = Promise[Boolean]()
    scheduler.at(dtAfter) {
      p.success((DateTime.now() - dt).unix().value < 10000)
    }
    p.future.map(assert(_))
  }

  ignore("fromNow") {
    assert(DateTime(2018, 10, 1, 1, 1, 1, 1).fromNow.format == "in 2 year(s)")
    assert(DateTime(2013, 2, 1, 1, 1, 1, 1).fromNow.format == "3 year(s) ago")
  }
}