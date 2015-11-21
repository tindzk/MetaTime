package pl.metastack.metatime

import minitest.SimpleTestSuite

object SchedulerTest extends SimpleTestSuite with TestIgnored {
  import Implicits._

  testIgnored("at()") {
    val task = Scheduler.at(DateTime.now() + 5.seconds) {
      ???
    }

    task.cancel()

    val task2 = Scheduler.at(5.minutes.fromNow) {
      ???
    }
  }

  testIgnored("every()") {
    val task = Scheduler.every(5.minutes) { case t =>
      t.cancel()
    }
  }
}
