package pl.metastack.metatime

case class Hour(value: Int) extends Time with Period
case class Minute(value: Int) extends Time with Period
case class Second(value: Int) extends Time with Period
case class Millisecond(value: Int) extends Time with Period

trait Time extends Ordered[Time] {
  def +(time: Time): Unit = ???

  def days: Days = ???

  def format: String = ???

  def date: Date = ???
  def dateTime: DateTime = ???

  def fromNow: Offset[Time] = ???

  override def compare(that: Time): Int = ???
}

object Time {
  def apply(hour: Int): Time = ???
  def apply(hour: Int, minute: Int): Time = ???

  /** Current time */
  def now(): Time = ???
}
