package pl.metastack.metatime

object Constants {
  final val YearIndex = 1
  final val MonthIndex = 2
  final val DayIndex = 3
  final val HourIndex = 4
  final val MinuteIndex = 5
  final val SecondIndex = 6
  final val MillisecondIndex = 7

  final val MillisInSecond = 1000
  final val SecondsInMinute = 60
  final val MillisInMinute = SecondsInMinute * MillisInSecond
  final val MinutesInHour = 60
  final val SecondsInHour = MinutesInHour * SecondsInMinute
  final val MillisInHour = MinutesInHour * MillisInMinute

  final val HoursInDay = 24
  final val MinutesInDay = HoursInDay * MinutesInHour
  final val SecondsInDay = HoursInDay * SecondsInHour
  final val MillisInDay = HoursInDay * MillisInHour
  final val DefaultYear = 1970
  final val DaysInMonth = 30
  final val DaysInYear = 365

  final val In = "in "
  final val Ago = " ago"
}

