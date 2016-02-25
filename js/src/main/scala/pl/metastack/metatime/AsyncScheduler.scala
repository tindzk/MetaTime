package pl.metastack.metatime

import scala.concurrent.duration._

import scala.scalajs.js

class AsyncScheduler extends Scheduler {
  type Timeout = js.Dynamic
  type Interval = js.Dynamic

  def setTimeout(delayMillis: Long, r: Runnable): Timeout = {
    val lambda: js.Function = () => r.run()
    js.Dynamic.global.setTimeout(lambda, delayMillis)
  }

  def setInterval(intervalMillis: Long, r: Runnable): Interval = {
    val lambda: js.Function = () => r.run()
    js.Dynamic.global.setInterval(lambda, intervalMillis)
  }

  def clearTimeout(task: Timeout): Unit =
    js.Dynamic.global.clearTimeout(task)

  def clearInterval(task: Interval): Unit =
    js.Dynamic.global.clearInterval(task)

  def schedule(interval: FiniteDuration, r: Runnable): Cancelable = {
    val task = setInterval(interval.toMillis, r)
    Cancelable(clearInterval(task))
  }

  def scheduleOnce(initialDelay: FiniteDuration, r: Runnable): Cancelable = {
    val task = setTimeout(initialDelay.toMillis, r)
    Cancelable(clearTimeout(task))
  }

  def at(time: Time)(f: => Unit)(implicit scheduler: Scheduler): Cancelable =
    at(time.fromNow())(f)

  def at(time: DateTime)(f: => Unit)(implicit schedular: Scheduler): Cancelable =
    at(time.fromNow())(f)

  def at(time: Offset[_])(f: => Unit)(implicit scheduler: Scheduler): Cancelable = {
    //Implement Millis in Component and replace below call 1000.millis with time.millis
    scheduler.scheduleOnce(1000.millis)(f)
  }

}