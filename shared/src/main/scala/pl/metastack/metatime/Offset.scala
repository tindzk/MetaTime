package pl.metastack.metatime

trait Formatter {
  def format(s: DateTime): String = ???
}

object Formatter {
  def JustNow: Any = ???
  def Ago(x: Any): Any = ???
  def Minutes: Any = ???
  def Hours: Any = ???
  def Days: Any = ???
  def DateTime(x: String): Any = ???

  def branch(ranges: Seq[(Range, Any)], fallback: Any): Formatter = ???
}

trait Offset[T] {
  def format: String = ???
}
