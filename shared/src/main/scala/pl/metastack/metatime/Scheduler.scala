package pl.metastack.metatime

import scala.concurrent.duration._

/**
  * Inspired from Monifu's scheduling code.
  */
trait Scheduler {
  def schedule(interval: FiniteDuration, r: Runnable): Cancelable
  def scheduleOnce(initialDelay: FiniteDuration, action: Runnable): Cancelable

  def schedule(interval: FiniteDuration)(action: => Unit): Cancelable =
    schedule(interval, new Runnable {
      def run(): Unit = action
    })

  def scheduleOnce(initialDelay: FiniteDuration)(action: => Unit): Cancelable =
    scheduleOnce(initialDelay, new Runnable {
      def run(): Unit = action
    })

  def currentTimeMillis(): Long = System.currentTimeMillis()
}

trait Cancelable {
  def cancel(): Boolean
}

object Cancelable {
  def apply(): Cancelable = apply({})

  def apply(callback: => Unit): Cancelable =
    new Cancelable {
      var isCanceled = false

      def cancel(): Boolean =
        if (isCanceled) false
        else {
          isCanceled = true
          callback
          true
        }
    }
}

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
