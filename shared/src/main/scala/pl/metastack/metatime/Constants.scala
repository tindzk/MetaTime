package pl.metastack.metatime

object Constants {
    final val YEAR_INDEX = 1
    final val MONTH_INDEX = 2
    final val DAY_INDEX = 3
    final val HOUR_INDEX = 4
    final val MINUTE_INDEX = 5
    final val SECOND_INDEX = 6
    final val MILLISECOND_INDEX = 7

    final val MILLIS_IN_SECOND = 1000
    final val SECONDS_IN_MINUTE = 60
    final val MILLIS_IN_MINUTE = SECONDS_IN_MINUTE * MILLIS_IN_SECOND
    final val MINUTES_IN_HOUR = 60
    final val SECONDS_IN_HOUR = MINUTES_IN_HOUR * SECONDS_IN_MINUTE
    final val MILLIS_IN_HOUR = MINUTES_IN_HOUR * MILLIS_IN_MINUTE

    final val HOURS_IN_DAY = 24
    final val MINUTES_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR
    final val SECONDS_IN_DAY = HOURS_IN_DAY * SECONDS_IN_HOUR
    final val MILLIS_IN_DAY = HOURS_IN_DAY * MILLIS_IN_HOUR
    final val DEFAULT_YEAR = 1970

    final val IN = "in "
    final val AGO = " ago"
  }

