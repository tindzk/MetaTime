package pl.metastack.metatime

case class PatternTime(parts: Seq[Time => String] = Nil) extends Pattern[PatternTime, Time] {

  def amPm: PatternTime =
    add(time =>
      if (time.h > 12) "PM"
      else           "AM")

  def hour(amPm: Boolean = false): PatternTime = add(_.h.toString)
  def minute: PatternTime = add(time =>
    f"${time.m}%02d".toString)

  def minuteAgo: PatternTime = add(time =>
    time.fromNow.component.dateTime.m.toString)

  def hourAgo: PatternTime = add(time =>
    time.fromNow.component.dateTime.h.toString)

  def second: PatternTime = add(_.s.toString)

  def secondsFract(digits: Int): PatternTime = {
    add(_.ms.toString)
  }

  //def newLine: PatternTime = add(_ => "\n")

  def add(item: Time => String): PatternTime = copy(parts = parts ++ Seq(item))

  def format(time: Time): String =
    parts.foldLeft("") { case (string, format) => string + format(time) }

  def concat(pattern: PatternTime): PatternTime = PatternTime(parts ++ pattern.parts)

  def ++(pattern: PatternTime): PatternTime = concat(pattern)
}

object PatternTime {

  val DefaultTime = PatternTime().hour(amPm = true).colon.minute.space.amPm

  val JustNow = PatternTime().string("just now")

  def ago(pattern: PatternTime): PatternTime = pattern.space.string("ago")

  val MinutesAgo: PatternTime = ago(PatternTime().minuteAgo.space.string("minute(s)"))
  val HoursAgo: PatternTime = ago(PatternTime().hourAgo.space.string("hour(s)"))
  val DaysAgo: PatternTime = ago(PatternTime().minute.space.string("day(s)"))

  def branch(ranges: Seq[(Range, PatternTime)], fallback: PatternTime): PatternTime = {
    PatternTime().add { time =>
      val unixVal = time.fromNow.component.unix().value
      val x =ranges.find { case (r, p) =>
        (r.lowerBound to r.upperBound).contains(unixVal)
      }
      x.fold(fallback.format(time)) { case (r, p) => p.format(time) }
    }
  }
}