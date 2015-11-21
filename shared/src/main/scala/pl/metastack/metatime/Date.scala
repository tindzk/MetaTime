package pl.metastack.metatime

case class Year(value: Int)
  extends Date with DateComponent

case class Month(value: Int)
  extends Date with DateComponent

case class Day(value: Int)
  extends Date with DateComponent

case class Days(days: Int, hours: Int = 0) {
  def truncate: Day = Day(days)
}

trait DateComponent {
  def date: Date = ???
}

trait Date {
  def +(day: DateComponent): Date = ???

  def until(day: Date): Period = ???
}

object Date {
  def apply(year: Int): Date = ???
  def apply(year: Int, month: Int): Date = ???
  def apply(year: Int, month: Int, day: Int): Date = ???
  def parse(format: String, timezone: Timezone): Date = ???
}
