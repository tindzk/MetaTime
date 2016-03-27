package pl.metastack.metatime

case class PatternDateTime(parts: Seq[DateTime => String] = Nil) extends Pattern {
  def string(s: String): PatternDateTime = add(_ => s)

  def dash : PatternDateTime = string("-")
  def space: PatternDateTime = string(" ")
  def colon: PatternDateTime = string(":")
  def comma: PatternDateTime = string(",")
  def dot  : PatternDateTime = string(".")

  def year(short: Boolean = false): PatternDateTime =
    add(dt =>
      if (!short) dt.year.toString
      else        dt.year.toString.drop(2))

  def month: PatternDateTime = add(_.month.toString)

  val months = Seq("January", "February", "March",
    "April", "May", "June", "July", "August",
    "September", "October", "November", "December")

  val weekDays = Seq("Thursday", "Friday", "Saturday",
    "Sunday", "Monday", "Tuesday", "Wednesday")

  def monthName(short: Boolean = false): PatternDateTime = {
    add(dateTime =>
      months(dateTime.month - 1))
  }

  def day: PatternDateTime = add(_.day.toInt.toString)

  def amPm: PatternDateTime =
    add(dt =>
      if (dt.asInstanceOf[Time].h > 12) "PM"
      else           "AM")

  def hour(amPm: Boolean = false): PatternDateTime = add(_.asInstanceOf[Time].h.toString)
  def minute: PatternDateTime = add(dt =>
    f"${dt.asInstanceOf[Time].m}%02d".toString)

  def minuteAgo: PatternDateTime = add(dt =>
    dt.asInstanceOf[Time].fromNow.component.dateTime.m.toString)

  def hourAgo: PatternDateTime = add(dt =>
    dt.asInstanceOf[Time].fromNow.component.dateTime.h.toString)

  def second: PatternDateTime = add(_.asInstanceOf[Time].s.toString)

  /** Fractional seconds padded to `digits` */
  def secondsFract(digits: Int): PatternDateTime = {
    add(_.asInstanceOf[Time].ms.toString)
  }

  /**
    * Day of the week such as Monday, Tuesday, Wednesday etc.
    * @param short Short version of weekdays (Mon, Tue, Wed)
    */
  def weekDay(short: Boolean = false): PatternDateTime = {
    add(dateTime =>
      weekDays(dateTime.weekDay() - 1))
  }

  def newLine: PatternDateTime = add(_ => "\n")

  def add(item: DateTime => String): PatternDateTime = copy(parts = parts ++ Seq(item))

  def format(dateTime: DateTime): String =
    parts.foldLeft("") { case (string, format) => string + format(dateTime) }

  def concat(pattern: PatternDateTime): PatternDateTime = PatternDateTime(parts ++ pattern.parts)

  def ++(pattern: PatternDateTime): PatternDateTime = concat(pattern)
}

object PatternDateTime {
  val Iso8601 = PatternDateTime()
    .year().dash.month.dash.day.space
    .hour().colon.minute.colon.second.dot.secondsFract(3)
    .string("Z")

  // Tuesday, 1 March 2016
  val DefaultDate = PatternDateTime().weekDay().comma.space.day.space.monthName().space.year()

  // 6:57 PM
  val DefaultTime = PatternDateTime().hour(amPm = true).colon.minute.space.amPm

  val DefaultDateTime = DefaultDate ++ DefaultTime

  val JustNow = PatternDateTime().string("just now")

  def ago(pattern: PatternDateTime): PatternDateTime = pattern.space.string("ago")

  val MinutesAgo: PatternDateTime = ago(PatternDateTime().minuteAgo.space.string("minute(s)"))
  val HoursAgo: PatternDateTime = ago(PatternDateTime().hourAgo.space.string("hour(s)"))
  val DaysAgo: PatternDateTime = ago(PatternDateTime().minute.space.string("day(s)"))

  def branch(ranges: Seq[(Range, PatternDateTime)], fallback: PatternDateTime): PatternDateTime = {
    PatternDateTime().add { dt =>
    val unixVal = dt.fromNow.component.unix().value
    val x =ranges.find { case (r, p) =>
      (r.lowerBound to r.upperBound).contains(unixVal)
    }
    x.fold(fallback.format(dt)) { case (r, p) => p.format(dt) }
    }
  }
}