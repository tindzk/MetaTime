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

  testIgnored("in()") {
    val task = Scheduler.in(5.minutes) {
      ???
    }
  }

  testIgnored("every()") {
    var counter = 0
    val task = Scheduler.every(5.minutes) { case t =>
      if (counter > 2) t.cancel()
      counter += 1
    }
  }
}
