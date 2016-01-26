package pl.metastack.metatime

trait Task {
  def cancel(): Unit = ???
}

object Scheduler {
  def at(time: Time)(f: => Unit): Task = ???
  def at(time: DateTime)(f: => Unit): Task = ???
  def at(time: Offset[Time])(f: => Unit): Task = ???

  def in(time: Period)(f: => Unit): Task = ???

  def every(period: Period)(f: Task => Unit): Task = ???
  def every(period: Period, offset: Offset[_])(f: Task => Unit): Task = ???
}
