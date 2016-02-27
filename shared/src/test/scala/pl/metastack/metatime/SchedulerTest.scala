package pl.metastack.metatime

import org.scalatest.{AsyncFunSuite}
import scala.concurrent.{Promise}

class SchedulerTest extends AsyncFunSuite  {

  test("Schedule at Time") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val timeBefore = Time.now()
    val p = Promise[Boolean]()
      scheduler.at(Time(0, 0, 10, 0)) {
        p.success(Math.abs(Time.now().unix().value - timeBefore.unix().value) > 10000)
      }
    p.future.map (res => assert(res, true))
  }

  test("Schedule at DateTime)") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val dt = DateTime.now()
    val dtAfter = (dt + Second(5)).asInstanceOf[DateTime]
    val p = Promise[Boolean]()
      scheduler.at(dtAfter) {
      p.success(Math.abs(DateTime.now().unix().value - dt.unix().value) < 10000)
    }
    p.future.map (res => assert(res, true))
  }

  test("Schedule at Offset") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val timeBefore = Time.now()
    val offset = Offset(Second(10))
    val p = Promise[Boolean]()
    scheduler.at(offset) {
      p.success(Math.abs(Time.now().unix().value - timeBefore.unix().value) > 10000)
    }
    p.future.map (res => assert(res, true))
  }

  ignore("Schedule at Time Every") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val timeBefore = Time.now()
    val p = Promise[Boolean]()
    scheduler.every(Time(0, 0, 10, 0)) {
      p.success(Math.abs(Time.now().unix().value - timeBefore.unix().value) > 10000)
    }
    p.future.map (res => assert(res, true))
  }

  ignore("Schedule at DateTime Every)") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val dt = DateTime.now()
    val dtAfter = (dt + Second(5)).asInstanceOf[DateTime]
    val p = Promise[Boolean]()
    scheduler.every(dtAfter) {
      p.success(Math.abs(DateTime.now().unix().value - dt.unix().value) < 10000)
    }
    p.future.map (res => assert(res, true))
  }

  ignore("Schedule at Offset Every") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val timeBefore = Time.now()
    val offset = Offset(Second(10))
    val p = Promise[Boolean]()
    scheduler.every(offset) {
      p.success(Math.abs(Time.now().unix().value - timeBefore.unix().value) > 10000)
    }
    p.future.map (res => assert(res, true))
  }
}