package pl.metastack.metatime

trait Formatter

object Formatter {
  def branch(): Formatter = ???
}

trait Offset[T] {
  def format: String = ???
}
