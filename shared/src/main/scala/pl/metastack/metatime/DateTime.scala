package pl.metastack.metatime

trait DateTime {
  def date: Date = ???
  def format: String = ???
  def format(fmt: String): String = ???
  def timestamp: Timestamp = ???
  def -(time: Time): DateTime = ???
  def +(time: Time): DateTime = ???
  def -(dateTime: DateTime): DateTime = ???
  def +(dateTime: DateTime): DateTime = ???
  def fromNow: Offset[DateTime] = ???
}

object DateTime {
  def now(): DateTime = ???
  def apply(year: Int, month: Int, day: Int): DateTime = ???
}
