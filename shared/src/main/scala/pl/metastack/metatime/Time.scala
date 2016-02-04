package pl.metastack.metatime

trait Component extends Ordered[Component] {

  def until(component: Component): Period = ???

  def days: Day = Day(unix().value / 0.0)

  def format: String = ???
  def date: Date = Date(this)
  def dateTime: DateTime = DateTime(this)

  def fromNow: Offset[Time] = ???

  def to(until: Component): Range = ???

  def unix(): Unix

  override def compare(that: Component): Int = {
    unix().value.compare(that.unix().value)
  }

  def yearsFromDefault(years: Int) = years - 1970

  def calculateOffset(smallerTime: Component, biggerTime: Component): Component = {
    val unixDiff = biggerTime.unix().value - smallerTime.unix().value
    if(unixDiff <= 86400000) {
      val timeObj = Time(unixDiff)
      val timeNow = Time(biggerTime)
      if((timeObj.h == timeNow.h) && (timeObj.m == timeNow.m)){
        Second(timeObj.s)
      }
      else if(timeObj.h == timeNow.h) {
        Minute(timeNow.m - timeObj.m)
      }
      else
        Hour(timeObj.h)
    }
    else {
      val dateObj = Date(unixDiff)
      val dateNow = Date(biggerTime)
      if((yearsFromDefault(dateObj.year) == 0) && (dateObj.month == dateNow.month))
        Day(dateObj.day)
      else if(yearsFromDefault(dateObj.year) == 0)
        Month(dateObj.month)
      else
        Year(yearsFromDefault(dateObj.year))
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
        case _: Time => Time()
        case _: Date => DateTime()
        case _: DateTime => DateTime()
      }
      case _: Date => thatComp match {
        case _: Date => Date()
        case _: Time => DateTime()
        case _: DateTime => DateTime()
      }
    }
  }

  def +(other: Component): Component = {
    operationResultType(other) match {
      case _: DateTime =>
        DateTime(millisecond = ((unix().value) + (other.unix().value)))
      case _: Time =>
        Time(milliseconds = ((unix().value) + (other.unix().value)))
      case _: Date =>
        Date(milliseconds = ((unix().value) + (other.unix().value)))

    }
  }

  def -(other: Component): Component = {
    operationResultType(other) match {
      case _: Time =>
        Time(milliseconds = ((unix().value) - (other.unix().value)))
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
    else { accumulatedDays(month-1) }
  }

  def daysOfYear(y: Int) : Int = {
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

trait Hour extends Period with Component {
  val h: Int

 // override def fromNow = HourOffset(h)

  override def unix(): Unix = Unix(h * Minute(60).unix().value)

  override def equals(that: Any): Boolean =
    that match {
      case other: Hour => other.h == h
      case _ => false
    }
}

object Hour {
  def apply(value: Int): Hour = new Hour {
    override val h = value
  }
}

trait Minute extends Period with Component {
  val m: Int

  override def unix(): Unix = Unix(m * Second(60).unix().value)

  override def equals(that: Any): Boolean =
    that match {
      case other: Minute => other.m == m
      case _ => false
    }
}

object Minute {
  def apply(value: Int): Minute = new Minute {
    override val m = value
  }
}

trait Second extends Period with Component {
  val s: Int

  override def unix(): Unix = Unix(s * Millisecond(1000).unix().value)

  override def equals(that: Any): Boolean =
    that match {
      case other: Second => other.s == s
      case _ => false
    }
}

object Second {
  def apply(value: Int): Second = new Second {
    override val s = value
  }
}

trait Millisecond extends Period with Component {
  val ms: Int

  override def unix(): Unix = Unix(ms)

  override def equals(that: Any): Boolean =
    that match {
      case other: Millisecond => other.ms == ms
      case _ => false
    }
}

object Millisecond {
  def apply(value: Int): Millisecond =
    new Millisecond {
      override val ms = value
    }
}

trait Time extends Hour with Minute with Second with Millisecond {

  override def equals(that: Any): Boolean =
    that match {
      case other: Time => other.h == h && other.m == m && other.s == s && other.ms == ms
      case _ => false
    }

  override def unix(): Unix = Unix(
    Hour(h).unix().value + 
    Minute(m).unix().value +
    Second(s).unix().value + 
    Millisecond(ms).unix().value)
}

object Time {
  def apply(hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            millisecond: Int = 0): Time = new Time {
    override val h: Int = hour
    override val m: Int = minute
    override val s: Int = second
    override val ms: Int = millisecond
  }

  def apply(comp : Component) : Time = {
    comp match {
      case t: Time =>
        Time(hour = t.h,
        minute = t.m,
        second = t.s,
        millisecond = t.ms)
      case hr: Hour => Time(hour = hr.h)
      case m: Minute => Time(minute = m.m)
      case s: Second => Time(second = s.s)
      case ms: Millisecond => Time(millisecond = ms.ms)
    }
  }

  def apply(milliseconds: Long): Time = {
    val hour = ((milliseconds /
      (Hour(1).unix().value)).toInt) % 24
    val minute = ((milliseconds %
      (Hour(1).unix().value)) /
      (Minute(1).unix().value)).toInt
    val second = (((milliseconds %
      (Hour(1).unix().value)) %
      (Minute(1).unix().value)) /
      Second(1).unix().value).toInt
    val millisecond = ((((milliseconds %
      (Hour(1).unix().value)) %
      (Minute(1).unix().value)) %
      Second(1).unix().value) /
      Millisecond(1).unix().value).toInt
    Time(hour, minute, second, millisecond)
  }

  /** Current time */
  def now(): Time = {
    val currTimeInMilliSec: Long = System.currentTimeMillis()
    new Time {
      override val h: Int = (((currTimeInMilliSec / 1000) / 3600) % 24).toInt
      override val m: Int = (((currTimeInMilliSec / 1000) / 60) % 60).toInt
      override val s: Int = ((currTimeInMilliSec / 1000) % 60).toInt
      override val ms: Int = (currTimeInMilliSec % 1000).toInt
    }
  }
}
