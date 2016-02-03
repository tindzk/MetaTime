package pl.metastack.metatime

trait DateTime extends Time with Date {
  def format(fmt: String): String = ???
  def timestamp: Timestamp = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: DateTime => other.year == year && other.month == month && other.day == day &&
        h == other.h && m == other.m && s == other.s && ms == other.ms
      case _ => false
    }

  override def unix(): Unix = Unix(
    Year(year).unix().value +
    Month(month).unix().value +
    Day(day).unix().value +
    Hour(h).unix().value +
    Minute(m).unix().value +
    Second(s).unix().value +
    Millisecond(ms).unix().value)
}

object DateTime {

  def calculateDateTime(milliDate: Long, milliTime: Long): DateTime = {
    val date = Date(milliDate)
    val time = Time(milliTime)
    DateTime(date.year, date.month, date.day, time.h, time.m, time.s, time.ms).asInstanceOf[DateTime]
  }

  def now(): DateTime = calculateDateTime(Date.now().unix().value, Time.now.unix().value)
  def apply(millisecond: Long): DateTime = calculateDateTime(millisecond, millisecond)


  def apply(yr: Int, mo: Int, dy: Int,
            hour: Int, minute: Int, second: Int, millisecond: Int): DateTime =
    new DateTime {
      override val year: Int = yr
      override val month: Int = mo
      override val day: Double = dy
      override val h: Int = hour
      override val m: Int = minute
      override val s: Int = second
      override val ms: Int = millisecond
    }

  def apply(yr: Int = 0,  mo: Int = 1, dy: Double = 1.0,
            hr: Int = 0, min: Int = 0, sec: Int = 0,
            milli: Int = 0): Date = new DateTime {
    override val year: Int = yr
    override val month: Int = mo
    override val day: Double = dy
    override val h: Int = hr
    override val m: Int = min
    override val s: Int = sec
    override val ms: Int = milli
  }

  def apply(comp : Component) : DateTime = {
    comp match {
      case dT: DateTime =>
        DateTime(dT.year, dT.month, dT.day, dT.h, dT.m, dT.s, dT.ms).asInstanceOf[DateTime]

      case tempYear: Year =>
        DateTime(tempYear.year, 1, 1, 0, 0, 0, 0)

      case tempMonth: Month =>
        DateTime(0, tempMonth.month, 1, 0, 0, 0, 0)

      case tempDay: Day =>
        DateTime(0, 1, tempDay.day, 0, 0, 0, 0).asInstanceOf[DateTime]

      case tempHour: Hour =>
        DateTime(0, 1, 1, tempHour.h, 0, 0, 0)

      case tempMinute: Minute =>
        DateTime(0, 1, 1, 0, tempMinute.m, 0, 0)

      case tempSecond: Second =>
        DateTime(0, 1, 1, 0, 0, tempSecond.s, 0)

      case tempMilliSecond: Millisecond =>
        DateTime(0, 1, 1, 0, 0, 0, tempMilliSecond.ms)
    }
  }
}
