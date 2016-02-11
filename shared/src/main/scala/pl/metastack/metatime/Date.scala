package pl.metastack.metatime

trait Year extends Component {
  val year: Int

  override def unix(): Unix = Unix(daysOf(this).toLong * 24 * 3600 * 1000)

  override def equals(that: Any): Boolean =
    that match {
      case other: Year => other.year == year
      case _ => false
    }
}

object Year {
  def apply(value: Int): Year = new Year {
    override val year = value
  }
}

trait Month extends Component {
  val month: Int

  override def unix(): Unix = Unix(daysOf(this).toLong * 24 * 3600 * 1000)

  override def equals(that: Any): Boolean =
    that match {
      case other: Month => other.month == month
      case _ => false
    }
}

object Month {
  def apply(value: Int): Month = new Month {
    override val month = value
  }
}

trait Day extends Component {
  val day: Double

  override def unix(): Unix = Unix((day * 24 * 3600 * 1000).toLong)

  override def equals(that: Any): Boolean =
    that match {
      case other: Day => other.day == day
      case _ => false
    }

  def truncate: Day = Day(Math.round(day))
}

object Day {
  def apply(value: Double): Day = new Day {
    override val day = value
  }
}

trait Date extends Year with Month with Day {

  override def equals(that: Any): Boolean =
    that match {
      case other: Date => other.year == year && other.month == month && other.day == day
      case _ => false
    }

  override def unix(): Unix = Unix(
    Year(year).unix().value +
    Month(month).unix().value +
    Day(day).unix().value)
}

object Date {
  def apply(y: Int): Date = new Date {
    override val year: Int = y
    override val month: Int = 1
    override val day: Double = 1.0
  }
  def apply(year: Int, month: Int): Date = ???
  def parse(format: String, timezone: Timezone): Date = ???

  val YearsSequence = Seq(365, 365, 366, 365)
  val DaysInMonths = Seq(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  val LeapDaysInMonths = Seq(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

  def isLeapYear(year: Int): Boolean = (year % 4) == 0
  def accumulate(f: Int => Seq[Int], y: Int): Seq[Int] = f(y)
  def accuPrevElements(seq: Seq[Int]) = seq.scanLeft(0)(_ + _).tail

  def accuYears(totalDays: Int) : Seq[Int] = {
    val yearLength = Stream.continually(YearsSequence).flatten.take((totalDays / 365) + 1).toList
    accuPrevElements(yearLength)
  }

  def yearsPassed(totalDays: Int) : Int = {
    accumulate(accuYears, totalDays).zipWithIndex.indexWhere( {
      case (accDays, year) => totalDays <= accDays
    }) + 1970
  }

  def daysPassed(totalDays: Int) : Int = {
    val accumulatedDaysList = accumulate(accuYears, totalDays).filter(x => x <= totalDays)
    accumulatedDaysList.length match {
      case 0 => totalDays
      case n => totalDays - accumulatedDaysList(n - 1)
    }
  }

  def findMonth(days: Int, year: Int): Int = {
    accumulate(accuDays, year).filter(x => x <= days).length + 1
  }

  def accuDays(year: Int): Seq[Int] = {
    if(isLeapYear(year))
      accuPrevElements(LeapDaysInMonths)
    else
      accuPrevElements(DaysInMonths)
  }

  def findDay(days: Int, year: Int): Int = {
    val accumulatedDays = accumulate(accuDays, year).filter(x => x <= days)
    accumulatedDays.length match {
      case 0 => days
      case n => days - accumulatedDays(n - 1)
    }
  }

  def milliToDays(milliSeconds: Long, flagNow: Boolean): Int = {
      if((milliSeconds % (60 * 60 * 24 * 1000) > 0) && (flagNow)){
        (((milliSeconds / (60 * 60 * 24 * 1000))).toInt) + 1
      }
      else { ((milliSeconds / (60 * 60 * 24 * 1000))).toInt }
    }

  def calculateDate(totalDays: Int): Date =
  {
    val year = yearsPassed(math.abs(totalDays))
    val daysOfThisYear = daysPassed(math.abs(totalDays))
    val month = findMonth(daysOfThisYear, year)
    val day = findDay(daysOfThisYear, year)
    if(totalDays <= 0) Date(-year, -month, -day)
    else Date(year, month, day)
  }

  def now(): Date = {
    val currTimeInMilliSec: Long = System.currentTimeMillis()
    calculateDate(milliToDays(currTimeInMilliSec, true))
  }

  def apply(milliseconds: Long): Date = {
    calculateDate(milliToDays(milliseconds, false))
  }

  def apply(comp : Component) : Date = {
    comp match {
      case date: Date =>
        Date(y = date.year,
          m = date.month,
          d = date.day)
      case d: Day => Date(d = d.day)
      case month: Month => Date(m = month.month)
      case y: Year => Date(y = y.year)
    }
  }

  def apply(y: Int = 0,
    m: Int = 1,
    d: Double = 1.0): Date = new Date {
      override val year: Int = y
      override val month: Int = m
      override val day: Double = d
  }
}
