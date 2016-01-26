package pl.metastack.metatime

trait Formatter {
  def format(s: DateTime): String = ???
}

object Formatter {
  def JustNow: Any = ???
  def Ago(x: Any): Any = ???
  def Minutes: Any = ???
  def Hours: Any = ???
  def Days: Any = ???
  def DateTime(x: String): Any = ???

  def branch(ranges: Seq[(Range, Any)], fallback: Any): Formatter = ???
}

trait Offset[T] {
  def format: String = ???
}

/* case class DateOffset(hour: Int) extends Offset
case class YearOffset(hour: Int) extends Offset
case class MonthOffset(hour: Int) extends Offset
case class DayOffset(hour: Int) extends Offset
case class TimeOffset(hour: Int) extends Offset
case class HourOffset(hour: Int) extends Offset
case class MinuteOffset(hour: Int) extends Offset
case class SecondOffset(hour: Int) extends Offset
case class MillisecondOffset(hour: Int) extends Offset */
