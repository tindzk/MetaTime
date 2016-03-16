package pl.metastack.metatime

import org.scalatest.AsyncFunSuite
import scala.concurrent.Promise

class SchedulerTest extends AsyncFunSuite {
  val scheduler: Scheduler = Platform.DefaultScheduler

  test("Schedule once at Time") {
    val timeBefore = Time.now()
    val p = Promise[Boolean]()
    scheduler.at(Time(0, 0, 1, 0)) {
      p.success((Time.now() - timeBefore).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  test("Schedule once at DateTime") {
    val dt = DateTime.now()
    val dtAfter = (dt + Second(1)).asInstanceOf[DateTime]
    assert(dtAfter.unix().value == dt.unix().value + 1000)

    val p = Promise[Boolean]()
    scheduler.at(dtAfter) {
      p.success((DateTime.now() - dt).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  test("Schedule once at Offset") {
    val timeBefore = Time.now()
    val offset = Offset(Second(1))
    val p = Promise[Boolean]()
    scheduler.at(offset) {
      p.success((Time.now() - timeBefore).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  ignore("Schedule repeatedly at Time") {
    val timeBefore = Time.now()
    val p = Promise[Boolean]()
    scheduler.every(Time(0, 0, 1, 0)) {
      // TODO Check whether every() is called more than once in the same intervals
      p.success((Time.now() - timeBefore).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  ignore("Schedule repeatedly at DateTime") {
    val dt = DateTime.now()
    val dtAfter = (dt + Second(1)).asInstanceOf[DateTime]
    val p = Promise[Boolean]()
    scheduler.every(dtAfter) {
      p.success((DateTime.now() - dt).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }

  ignore("Schedule repeatedly at Offset") {
    val timeBefore = Time.now()
    val offset = Offset(Second(1))
    val p = Promise[Boolean]()
    scheduler.every(offset) {
      p.success((Time.now() - timeBefore).unix().value >= 1000)
    }
    p.future.map(assert(_))
  }
}