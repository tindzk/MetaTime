package pl.metastack.metatime

import org.scalatest.{AsyncFunSuite}
import scala.concurrent.{Promise}

class SchedulerTest extends AsyncFunSuite  {

  test("at()") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    val p = Promise[Unit]()
      scheduler.at(Time(0, 0, 30, 0)) {
        p.success(())
      }
    p.future.map (_ => assert(true))
  }
}