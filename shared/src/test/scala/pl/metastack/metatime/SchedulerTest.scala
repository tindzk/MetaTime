package pl.metastack.metatime

import org.scalatest.FunSuite

class SchedulerTest extends FunSuite {
  import Implicits._

  ignore("at()") {
    val task = Scheduler.at(5.second.fromNow.component.asInstanceOf[DateTime]) {
      ???
    }

    task.cancel()

    val task2 = Scheduler.at(5.minutes.fromNow.component.asInstanceOf[DateTime]) {
      ???
    }
  }

  ignore("in()") {
    val task = Scheduler.in(5.minutes) {
      ???
    }
  }

  ignore("every()") {
    var counter = 0
    val task = Scheduler.every(5.minutes) { case t =>
      if (counter > 2) t.cancel()
      counter += 1
    }
  }
}