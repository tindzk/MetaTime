package pl.metastack.metatime

trait Pattern[+T,ItemType <: Component]{

  def string(s: String): T = add(_ => s)
  def newLine: T = add(_ => "\n")
  def dash : T = string("-")
  def space: T = string(" ")
  def colon: T = string(":")
  def comma: T = string(",")
  def dot  : T = string(".")

  def add(item: ItemType => String): T

  val months = Seq("January", "February", "March",
    "April", "May", "June", "July", "August",
    "September", "October", "November", "December")

  val weekDays = Seq("Thursday", "Friday", "Saturday",
    "Sunday", "Monday", "Tuesday", "Wednesday")
}