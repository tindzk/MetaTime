package pl.metastack.metatime

object Implicits {
  implicit class IntExtensions(value: Int) {
    def year: Year = Year(value)
    def years: Year = Year(value)
    def day: Day = Day(value)
    def days: Day = Day(value)
    def week: Day = Day(7 * value)
    def weeks: Day = Day(7 * value)
    def hour: Hour = Hour(value)
    def hours: Hour = Hour(value)
    def minute: Minute = Minute(value)
    def minutes: Minute = Minute(value)
    def second: Second = Second(value)
    def seconds: Second = Second(value)
  }
}
