package pl.metastack.metatime

trait Year extends Component {
  val y: Int

  override def unix(): Unix = Unix(daysOf(this).toLong * 24 * 3600 * 1000)

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

trait Month extends Component {
  val m: Int

  override def unix(): Unix = Unix(daysOf(this).toLong * 24 * 3600 * 1000)

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

trait Day extends Component {
  val d: Double

  override def unix(): Unix = Unix((d * 24 * 3600 * 1000).toLong)

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

trait Date extends Year with Month with Day {

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
  def apply(year: Int): Date = new Date {
    override val y: Int = year
    override val m: Int = 1
    override val d: Double = 1.0
  }
  def apply(year: Int, month: Int): Date = ???
  def parse(format: String, timezone: Timezone): Date = ???

  def findYearsOrRemainingDaysOfYear(totalDays: Int, year: Boolean) : Int = {
    if(year) { yearsPassed(totalDays)}
    else { daysPassed(totalDays) }
  }

  val YearsSequence = Seq(365, 365, 366, 365)

  def yearsPassed(totalDays: Int) : Int = {
    val yearLength = Stream.continually(YearsSequence).flatten.take((totalDays / 365) + 1).toList
    val accumulatedDays = yearLength.zipWithIndex.foldLeft(Seq.empty[Int]) {
      case (Nil, (cur, _)) => Seq(cur)
      case (acc, (cur, i)) => acc ++ Seq(acc.last + cur)
    }
    accumulatedDays.zipWithIndex.indexWhere( {
      case (accDays, year) => totalDays <= accDays
    }) + 1970
  }

  def daysPassed(totalDays: Int) : Int = {
    val yearLength = Stream.continually(YearsSequence).flatten.take((totalDays / 365) + 1).toList
    val accumulatedDays = yearLength.zipWithIndex.foldLeft(Seq.empty[Int]) {
      case (Nil, (cur, _)) => Seq(cur)
      case (acc, (cur, i)) => acc ++ Seq(acc.last + cur)
    }
    accumulatedDays.zipWithIndex.indexWhere( {
      case (accDays, year) => totalDays <= accDays
    }) match {
      case 0 => totalDays
      case n => totalDays - accumulatedDays(n-1)
    }
  }

  val DaysInMonths = Seq(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

  def isLeapYear(year: Int): Boolean = (year % 4) == 0


  def findMonthOrDay(days: Int, year: Int, month: Boolean): Int = {
    if(month) { findMonth(days, year)}
    else { findDay(days, year) }
  }

  def findMonth(days: Int, year: Int): Int = {
    val accumulatedDays = DaysInMonths.zipWithIndex.foldLeft(Seq.empty[Int]) {
      case (Nil, (cur, _)) => Seq(cur)
      case (acc, (cur, 1)) if isLeapYear(year) => acc ++ Seq(acc.last + 29)
      case (acc, (cur, i)) => acc ++ Seq(acc.last + cur)
    }
    accumulatedDays.zipWithIndex.indexWhere {
      case (accDays, month) => days <= accDays
    } + 1
  }

  def findDay(days: Int, year: Int): Int = {
    val accumulatedDays = DaysInMonths.zipWithIndex.foldLeft(Seq.empty[Int]) {
      case (Nil, (cur, _)) => Seq(cur)
      case (acc, (cur, 1)) if isLeapYear(year) => acc ++ Seq(acc.last + 29)
      case (acc, (cur, i)) => acc ++ Seq(acc.last + cur)
    }
    (accumulatedDays.zipWithIndex.indexWhere {
     case (accDays, month) => days <= accDays
    }) match {
      case 0 => days
      case n => days - accumulatedDays(n - 1)
    }
  }

  def calculateDate(currTimeMilliSec: Long): Date =
  {
    val totalDays = ((currTimeMilliSec / (60 * 60 * 24 * 1000))).toInt
    val year = findYearsOrRemainingDaysOfYear(totalDays, true)
    val daysOfThisyear = findYearsOrRemainingDaysOfYear(totalDays, false)
    val month = findMonthOrDay(daysOfThisyear, year, true)
    val day = findMonthOrDay(daysOfThisyear, year, false)
    val date =  Date(year, month, day)
    date
  }

  def now(): Date = {
    val currTimeInMilliSec: Long = System.currentTimeMillis()
    calculateDate(currTimeInMilliSec)
  }

  def apply(milliseconds: Long): Date = {
    calculateDate(milliseconds)
  }

  def apply(comp : Component) : Date = {
    comp match {
      case d: Date =>
        Date(year = d.y,
          month = d.m,
          day = d.d)
      case d: Day => Date(day = d.d)
      case m: Month => Date(month = m.m)
      case y: Year => Date(year = y.y)
    }
  }

  def apply(year: Int = 0,
    month: Int = 1,
    day: Double = 1.0): Date = new Date {
      override val y: Int = year
      override val m: Int = month
      override val d: Double = day
  }
}
