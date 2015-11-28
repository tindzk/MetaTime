package pl.metastack.metatime

trait Component extends Ordered[Component] {
  def +(time: Component): Unit = ???

  def days: Days = ???

  def format: String = ???

  def date: Date = ???
  def dateTime: DateTime = ???

  def fromNow: Offset[Time] = ???

  def to(until: Component): Range = ???

  override def compare(that: Component): Int = ???
}

trait Hour extends Period with Component {
  val h: Int
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

trait Time extends Hour with Minute with Second with Millisecond

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

  /** Current time */
  def now(): Time = ???
}
