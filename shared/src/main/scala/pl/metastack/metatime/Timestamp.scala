package pl.metastack.metatime

case class Unix(value: Long) extends Timestamp

trait Timestamp {
  def date: Date = ???
  def dateTime: DateTime = ???
}
