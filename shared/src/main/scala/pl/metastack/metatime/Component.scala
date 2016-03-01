package pl.metastack.metatime

import pl.metastack.metatime.Constants._

// TODO Rename Component to a better name
trait Component extends Ordered[Component] {
  def until(component: Component): Component = ???

  def days: Day = Day(unix().value / 0.0)

  def format(implicit locale: Locale): String = ???

  def date: Date = Date(this)

  def dateTime: DateTime = DateTime(this)

  def to(until: Component): Range = ???

  def unix(): Unix

  override def compare(that: Component): Int = {
    unix().value.compare(that.unix().value)
  }

  def milliseconds(): Long = {
    val thisComp = getConstructionType(this)
    thisComp match {
      case _: DateTime =>
        val dateTime = DateTime(this)
        Date(dateTime.year, dateTime.month, dateTime.day).milliseconds() +
          Time(dateTime.h, dateTime.m, dateTime.s, dateTime.ms).milliseconds()
      case _: Time =>
        val time = Time(this)
        time.h * MillisInHour + time.m * MillisInMinute + time.s * MillisInSecond + time.ms
      case _: Date =>
        val date = Date(this)
        date.year * DaysInYear * MillisInDay + date.month * DaysInMonth * MillisInDay + (date.day * MillisInDay).toLong
    }
  }

  def yearsFromDefault(years: Int) = years - DefaultYear

  def fromNow: Offset[Component] = Offset(calcOffset())

  def calcTimeDifference: Time = {
    val timeNow = Time.now()
    val otherTime = Time(unix().value)
    if ((otherTime.h == 0) && (otherTime.m == 0) && (otherTime.s == 0))
      Time(timeNow.h, timeNow.m, timeNow.s, otherTime.ms)
    else if (otherTime.h == 0 && otherTime.m == 0)
      Time(timeNow.h, timeNow.m, otherTime.s, otherTime.ms)
    else if (otherTime.h == 0)
      Time(timeNow.h, otherTime.m, otherTime.s, otherTime.ms)
    else
      Time(Time.now().unix().value - unix().value)
  }

  def calcDateDifference(): Date = {
    val otherDate = Date(unix().value)
    val nowDate = Date.now()

    if (yearsFromDefault(otherDate.year) == 0 && otherDate.month == 0)
      Date(nowDate.year, nowDate.month, otherDate.day)
    else if (yearsFromDefault(otherDate.year) == 0)
      Date(nowDate.year, otherDate.month, otherDate.day)
    else
      Date(nowDate.unix().value - otherDate.unix().value)
  }

  def calcDateTimeDifference(): DateTime = {
    val smDate = DateTime(unix().value)
    val dt = DateTime.now()
    if((yearsFromDefault(smDate.year) == 0) && (smDate.month == 0) && (smDate.day == 0)
      && (smDate.h == 0) && (smDate.m == 0) && (smDate.s == 0)) {
      DateTime(dt.year, dt.month, dt.day, dt.h, dt.m, dt.s, smDate.ms)
    }
    else if((yearsFromDefault(smDate.year) == 0) && (smDate.month == 0)
      && (smDate.day == 0) && (smDate.h == 0) && (smDate.m == 0)) {
      DateTime(dt.year, dt.month, dt.day, dt.h, dt.m, smDate.s, smDate.ms)
    }
    else if((yearsFromDefault(smDate.year) == 0) && (smDate.month == 0)
      && (smDate.day == 0) && (smDate.h == 0)) {
      DateTime(dt.year, dt.month, dt.day, dt.h, smDate.m, smDate.s, smDate.ms)
    }
    else if((yearsFromDefault(smDate.year) == 0) && (smDate.month == 0)
      && (smDate.day == 0)) {
      DateTime(dt.year, dt.month, dt.day, smDate.h, smDate.m, smDate.s, smDate.ms)
    }
    else if((yearsFromDefault(smDate.year) == 0) && (smDate.month == 0)) {
      DateTime(dt.year, dt.month, smDate.day, smDate.h, smDate.m, smDate.s, smDate.ms)
    }
    else if(yearsFromDefault(smDate.year) == 0) {
      DateTime(dt.year, smDate.month, smDate.day, smDate.h, smDate.m, smDate.s, smDate.ms)
    }
    else if(yearsFromDefault(smDate.year) == 0) {
      DateTime(dt.year, smDate.month, smDate.day, smDate.h, smDate.m, smDate.s, smDate.ms)
    }
    else {
      DateTime(dt.unix().value - smDate.unix().value)
    }
  }

  def firstValidUnit(dt: DateTime, unit: Int): Boolean = {
    val func = Seq[DateTime => Boolean](
      _.year - DefaultYear == 0,
      _.month == 1,
      _.day == 0,
      _.h == 0,
      _.m == 0,
      _.s == 0
    )
    func.slice(0, unit).forall(_(dt))
  }

  def mostSignificantTime(timeDiff: Time): Component = {
    val timeNow = Time.now()
    if(timeDiff.h == timeNow.h && timeDiff.m == timeNow.m && timeDiff.s == timeNow.s)
      Millisecond(timeDiff.ms)
    else if(timeDiff.h == timeNow.h && timeDiff.m == timeNow.m)
      Second(timeDiff.s)
    else if(timeDiff.h == timeNow.h || timeDiff.h == 0)
      Minute(timeDiff.m)
    else {
      Hour(timeNow.h - timeDiff.h)
    }
  }

  def absoluteDateTime(dt: DateTime): DateTime = {
    if(dt.unix().value <= 0)
      DateTime(math.abs(dt.year), math.abs(dt.month),
        math.abs(dt.day), math.abs(dt.h), math.abs(dt.m),
        math.abs(dt.s), math.abs(dt.ms))
    else
      dt
  }

  def mostSignificantDateTime(dt: DateTime): Component = {
    val dtDiff = absoluteDateTime(dt)
    if(firstValidUnit(dtDiff, SecondIndex))
      Millisecond(dt.ms)
    else if(firstValidUnit(dtDiff, MinuteIndex))
      Second(dt.s)
    else if(firstValidUnit(dtDiff, HourIndex))
      Minute(dt.m)
    else if(firstValidUnit(dtDiff, DayIndex))
      Hour(dt.h)
    else if(firstValidUnit(dtDiff, MonthIndex))
      Day(dt.day)
    else if(firstValidUnit(dtDiff, YearIndex)) {
      if(dt.month < 0)
        Month(dt.month + 1)
      else
        Month(dt.month - 1)
    }
    else if(dt.year < 0)
      Year(dt.year + DefaultYear)
    else
      Year(dt.year - DefaultYear)
  }

  def mostSignificantDate(dateDiff: Date): Component = {
    if((yearsFromDefault(dateDiff.year) == 0) && (dateDiff.month == 1))
      Day(dateDiff.day)
    else if(yearsFromDefault(dateDiff.year) == 0) {
      if(dateDiff.month < 0) Month(dateDiff.month + 1)
      else Month(dateDiff.month - 1)
    }
    else {
      if(dateDiff.year < 0)
        Year(dateDiff.year + DefaultYear)
      else
        Year(dateDiff.year - DefaultYear)
    }
  }

  def calcOffset(): Component = {
    if(math.abs(unix().value) <= MillisInDay)
      mostSignificantTime(calcTimeDifference)
    else getConstructionType(this) match {
      case _: DateTime =>
        mostSignificantDateTime(calcDateTimeDifference())
      case _: Date =>
        mostSignificantDate(calcDateDifference())
    }
  }

  def getConstructionType(other: Component) : Component = {
    other match {
      case _: DateTime => DateTime()
      case _: Time | _: Hour | _: Minute | _: Second | _: Millisecond => Time()
      case _: Date | _: Year | _: Month | _: Day => Date()
    }
  }

  def operationResultType(other: Component) : Component = {
    val thisComp = getConstructionType(this)
    val thatComp = getConstructionType(other)
    thisComp match {
      case _: DateTime => DateTime()
      case _: Time => thatComp match {
        case _: DateTime => DateTime()
        case _: Time => Time()
        case _: Date => DateTime()
      }
      case _: Date => thatComp match {
        case _: DateTime => DateTime()
        case _: Date => Date()
        case _: Time => DateTime()
      }
    }
  }

  def +(other: Component): Component = {
    operationResultType(other) match {
      case _: DateTime =>
        DateTime(millisecond = unix().value + other.unix().value)
      case _: Time =>
        Time(milliseconds = unix().value + other.unix().value)
      case _: Date =>
        Date(milliseconds = unix().value + other.unix().value)
    }
  }

  def -(other: Component): Component = {
    operationResultType(other) match {
      case _: DateTime =>
        DateTime(millisecond = unix().value - other.unix().value)
      case _: Time =>
        Time(milliseconds = unix().value - other.unix().value)
      case _: Date =>
        Date(milliseconds = unix().value - other.unix().value)
    }
  }

  val DaysInMonths = Seq(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  val YearsSequence = Seq(365, 365, 366, 365)

  def daysOfMonth(month: Int) : Int = {
    val accumulatedDays = DaysInMonths.zipWithIndex.foldLeft(Seq.empty[Int]) {
      case (Nil, (cur, _)) => Seq(cur)
      case (acc, (cur, i)) => acc ++ Seq(acc.last + cur)
    }
    if(month <= 1) 0
    else accumulatedDays(month - 2)
  }

  def daysOfYear(y: Int) : Int = {
    if(y < DefaultYear)
      Stream.continually(YearsSequence).flatten.take(y).toList.sum
    else
      Stream.continually(YearsSequence).flatten.take(yearsFromDefault(y)).toList.sum
  }

  def daysOf(component: Component) : Int = {
    component match {
      case d: Date => (d.day).toInt + daysOfMonth(d.month) + daysOfYear(d.year)
      case m: Month => daysOfMonth(m.month)
      case y: Year => daysOfYear(y.year)
    }
  }
}