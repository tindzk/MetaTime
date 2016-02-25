package pl.metastack.metatime

/**
  * Created by SyedWaqar on 2/22/2016.
  */
//package pl.metastack.metarx

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

object Platform {
  implicit lazy val DefaultScheduler: Scheduler = new AsyncScheduler(
    Executors.newSingleThreadScheduledExecutor(),
    ExecutionContext.Implicits.global)
}
