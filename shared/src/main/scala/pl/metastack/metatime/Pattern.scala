package pl.metastack.metatime

case class Pattern(parts: Seq[DateTime => String] = Nil) {
  def string(s: String): Pattern = add(_ => s)

  def dash : Pattern = string("-")
  def space: Pattern = string(" ")
  def colon: Pattern = string(":")
  def comma: Pattern = string(",")
  def dot  : Pattern = string(".")

  def year(short: Boolean = false): Pattern =
    add(dt =>
      if (!short) dt.year.toString
      else        dt.year.toString.drop(2))

  def month: Pattern = add(_.month.toString)

  def monthName(short: Boolean = false): Pattern = ???

  def day: Pattern = add(_.day.toString)

  def amPm: Pattern = ???

  def hour(amPm: Boolean = false): Pattern = add(_.h.toString)
  def minute: Pattern = add(_.m.toString.padTo(2, '0'))
  def second: Pattern = add(_.s.toString)

  /** Fractional seconds padded to `digits` */
  def secondsFract(digits: Int): Pattern = ???

  /**
    * Day of the week such as Monday, Tuesday, Wednesday etc.
    * @param short Short version of weekdays (Mon, Tue, Wed)
    */
  def weekDay(short: Boolean = false): Pattern = ???

  def newLine: Pattern = add(_ => "\n")

  def add(item: DateTime => String): Pattern = copy(parts = parts ++ Seq(item))

  def format(dateTime: DateTime): String =
    parts.foldLeft("") { case (string, format) => string + format(dateTime) }

  def concat(pattern: Pattern): Pattern = Pattern(parts ++ pattern.parts)

  def ++(pattern: Pattern): Pattern = concat(pattern)
}

object Pattern {
  val Iso8601 = Pattern()
    .year().dash.month.dash.day.space
    .hour().colon.minute.colon.second.dot.secondsFract(3)
    .string("Z")

  // Tuesday, 1 March 2016
  val DefaultDate = Pattern().weekDay().comma.day.space.monthName().space.year()

  // 6:57 PM
  val DefaultTime = Pattern().hour(amPm = true).colon.minute.space.amPm

  val DefaultDateTime = DefaultDate ++ DefaultTime

  val JustNow = Pattern().string("just now")

  def ago(pattern: Pattern): Pattern = pattern.space.string("ago")

  val MinutesAgo: Pattern = ago(Pattern().minute.space.string("minute(s)"))
  val HoursAgo  : Pattern = ago(Pattern().minute.space.string("hour(s)"))
  val DaysAgo   : Pattern = ago(Pattern().minute.space.string("day(s)"))

  def branch(ranges: Seq[(Range, Pattern)], fallback: Pattern): Pattern = ???
}
