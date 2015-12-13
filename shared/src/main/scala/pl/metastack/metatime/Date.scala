package pl.metastack.metatime

trait Year extends DateComponent {
  val y: Int

  override def unix(): Unix = Unix(Month(12).unix().value * y)

  override def equals(that: Any): Boolean =
    that match {
      case other: Year => other.y == y
      case _ => false
    }
}

object Year {
  def apply(value: Int): Year = new Year {
    override val y = value
  }
}

trait Month extends DateComponent {
  val m: Int

  override def unix(): Unix = Unix(Day(30).unix().value * m)

  override def equals(that: Any): Boolean =
    that match {
      case other: Month => other.m == m
      case _ => false
    }
}

object Month {
  def apply(value: Int): Month = new Month {
    override val m = value
  }
}

trait Day extends DateComponent {
  val d: Double

  override def unix(): Unix = Unix(Hour(24).unix().value * d.toLong)

  override def equals(that: Any): Boolean =
    that match {
      case other: Month => other.m == d
      case _ => false
    }

  def truncate: Day = Day(Math.round(d))
}

object Day {
  def apply(value: Double): Day = new Day {
    override val d = value
  }
}

trait DateComponent extends Component {
  override def date: Date = ???
  def until(day: DateComponent): Period = ???
}

trait Date extends Year with Month with Day {
  def -(day: DateComponent): Date = ???
  def +(day: DateComponent): Date = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: Date => other.y == y && other.m == m && other.d == d
      case _ => false
    }

  override def unix(): Unix = Unix(
    Year(y).unix().value +
    Month(m).unix().value +
    Day(d).unix().value)
  }

object Date {
  def now(): Date = ???
  def apply(year: Int): Date = ???
  def apply(year: Int, month: Int): Date = ???
  def parse(format: String, timezone: Timezone): Date = ???

  def apply(year: Int = 1970,
    month: Int = 1,
    day: Int = 1): Date = new Date {
      override val y: Int = year
      override val m: Int = month
      override val d: Double = day
  }
}
