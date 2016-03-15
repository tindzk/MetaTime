package pl.metastack.metatime

case class Pattern(parts: Seq[Component => String] = Nil) {
  def string(s: String): Pattern = add(_ => s)

  def dash : Pattern = string("-")
  def space: Pattern = string(" ")
  def colon: Pattern = string(":")
  def comma: Pattern = string(",")
  def dot  : Pattern = string(".")

  def year(short: Boolean = false): Pattern =
    add(dt =>
      if (!short) dt.asInstanceOf[DateTime].year.toString
      else        dt.asInstanceOf[DateTime].year.toString.drop(2))

  def month: Pattern = add(_.asInstanceOf[DateTime].month.toString)

  val months = Seq("January", "February", "March",
    "April", "May", "June", "July", "August",
    "September", "October", "November", "December")

  val weekDays = Seq("Thursday", "Friday", "Saturday",
    "Sunday", "Monday", "Tuesday", "Wednesday")

  def monthName(short: Boolean = false): Pattern = {
    add(dateTime =>
      months(dateTime.asInstanceOf[DateTime].month - 1))
  }

  def day: Pattern = add(_.asInstanceOf[DateTime].day.toInt.toString)

  def amPm: Pattern =
    add(dt =>
      if (dt.asInstanceOf[Time].h > 12) "PM"
      else           "AM")

  def hour(amPm: Boolean = false): Pattern = add(_.asInstanceOf[Time].h.toString)
  def minute: Pattern = add(dt =>
    f"${dt.asInstanceOf[Time].m}%02d".toString)
  def second: Pattern = add(_.asInstanceOf[Time].s.toString)

  /** Fractional seconds padded to `digits` */
  def secondsFract(digits: Int): Pattern = {
    add(_.asInstanceOf[Time].ms.toString)
  }

  /**
    * Day of the week such as Monday, Tuesday, Wednesday etc.
    * @param short Short version of weekdays (Mon, Tue, Wed)
    */
  def weekDay(short: Boolean = false): Pattern = {
    add(dateTime =>
      weekDays(dateTime.weekDay() - 1))
  }

  def newLine: Pattern = add(_ => "\n")

  def add(item: Component => String): Pattern = copy(parts = parts ++ Seq(item))

  def format(dateTime: DateTime): String =
    parts.foldLeft("") { case (string, format) => string + format(dateTime) }

  def format(time: Time): String =
    parts.foldLeft("") { case (string, format) => string + format(time) }

  def concat(pattern: Pattern): Pattern = Pattern(parts ++ pattern.parts)

  def ++(pattern: Pattern): Pattern = concat(pattern)
}

object Pattern {
  val Iso8601 = Pattern()
    .year().dash.month.dash.day.space
    .hour().colon.minute.colon.second.dot.secondsFract(3)
    .string("Z")

  // Tuesday, 1 March 2016
  val DefaultDate = Pattern().weekDay().comma.space.day.space.monthName().space.year()

  // 6:57 PM
  val DefaultTime = Pattern().hour(amPm = true).colon.minute.space.amPm

  val DefaultDateTime = DefaultDate ++ DefaultTime

  val JustNow = Pattern().string("just now")

  def ago(pattern: Pattern): Pattern = pattern.space.string("ago")

  val MinutesAgo: Pattern = ago(Pattern().minute.space.string("minute(s)"))
  val HoursAgo  : Pattern = ago(Pattern().minute.space.string("hour(s)"))
  val DaysAgo   : Pattern = ago(Pattern().minute.space.string("day(s)"))

  def branch(ranges: Seq[(Range, Pattern)], fallback: Pattern): Pattern = {
    println("Inside BRANCH ")
    for ((range, pattern) <- ranges) {
      println("Lower Bound => " + range.lowerBound)
      println("Upper Bound => " + range.upperBound)
      //if(unix.value >= range.lowerBound) & (unix.value() <= range.upperBound) {
      //  pattern
      //  break
     // }
    }
    fallback
  }
}
