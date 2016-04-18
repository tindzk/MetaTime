package pl.metastack.metatime.manual

import pl.metastack.metadocs.SectionSupport
import pl.metastack.metatime._

import scala.concurrent.Promise

object Examples extends SectionSupport {
  section("introduction") {
    DateTime(2015, 1, 1).format(PatternDateTime.DefaultDate)
  }

  section("formatting") {
    DateTime(2015, 1, 1).format(PatternDateTime.DefaultDate)
  }
  section("subtraction") {
    Date(2015, 1, 20) - Day(1)
  }

  section("addition") {
    Date(2015, 1, 20) + Day(1)
  }

  section("scheduler") {
    val scheduler: Scheduler = Platform.DefaultScheduler
    scheduler.at(DateTime(2016, 5, 1, 0, 0, 0, 0)) {
      //TASK
    }
  }
}
