package pl.metastack.metatime

case class Year(value: Int)
  extends Date with DateComponent {
  def unix(): Unix = ???
}

case class Month(value: Int)
  extends Date with DateComponent {
  def unix(): Unix = ???
}

case class Day(value: Double)
  extends Date with DateComponent {
  def unix(): Unix = ???
  def truncate: Day = Day(Math.round(value))
}

trait DateComponent extends Component {
  override def date: Date = ???
}

trait Date {
  def -(day: DateComponent): Date = ???
  def +(day: DateComponent): Date = ???

  def until(day: Date): Period = ???
}

object Date {
  def now(): Date = ???
  def apply(year: Int): Date = ???
  def apply(year: Int, month: Int): Date = ???
  def apply(year: Int, month: Int, day: Int): Date = ???
  def parse(format: String, timezone: Timezone): Date = ???
}
