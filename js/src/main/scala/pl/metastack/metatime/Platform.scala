package pl.metastack.metatime

object Platform {
  implicit lazy val DefaultScheduler: Scheduler = new AsyncScheduler
}