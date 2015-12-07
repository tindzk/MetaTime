package pl.metastack.metatime

trait Component extends Ordered[Component] {
  def -(time: Component): Component = ???
  def +(time: Component): Component = ???

  def days: Day = Day(unix().value / 0.0)

  def format: String = ???

  def date: Date = ???
  def dateTime: DateTime = ???

  def fromNow: Offset[Time] = ???

  def to(until: Component): Range = ???

  def unix(): Unix

  override def compare(that: Component): Int =
    unix().value.compare(that.unix().value)
}

trait Hour extends Period with Component {
  val h: Int

  override def unix(): Unix = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: Hour => other.h == h
      case _ => false
    }
	
  def <(that: Hour): Boolean = {
    if (!that.isInstanceOf[Hour])
      error("cannot compare " + that + " and an hour")
    val other = that.asInstanceOf[Hour]
    (h < other.h) 
  }
}

object Hour {
  def apply(value: Int): Hour = new Hour {
    override val h = value
  }
}

trait Minute extends Period with Component {
  val m: Int

  override def unix(): Unix = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: Minute => other.m == m
      case _ => false
    }
	
    def <(that: Minute): Boolean = {
		if (!that.isInstanceOf[Minute])
			error("cannot compare " + that + " and a minute")
		val other = that.asInstanceOf[Minute]
		(m < other.m) 
	}
}

object Minute {
  def apply(value: Int): Minute = new Minute {
    override val m = value
  }
}

trait Second extends Period with Component {
  val s: Int

  override def unix(): Unix = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: Second => other.s == s
      case _ => false
    }

  def <(that: Second): Boolean = {
	if (!that.isInstanceOf[Second])
		error("cannot compare " + that + " and a Second")
	val other = that.asInstanceOf[Second]
	(s < other.s) 
  }
}

object Second {
  def apply(value: Int): Second = new Second {
    override val s = value
  }
}

trait Millisecond extends Period with Component {
  val ms: Int

  override def unix(): Unix = ???

  override def equals(that: Any): Boolean =
    that match {
      case other: Millisecond => other.ms == ms
      case _ => false
    }
	
  def <(that: Millisecond): Boolean = {
    if (!that.isInstanceOf[Millisecond])
      error("cannot compare " + that + " and a Millisecond")
    val other = that.asInstanceOf[Millisecond]
    (ms < other.ms) 
  }
}

object Millisecond {
  def apply(value: Int): Millisecond =
    new Millisecond {
      override val ms = value
    }
}

trait Time extends Hour with Minute with Second with Millisecond
{
  def <(that : Time) : Boolean = {
    (h < that.h) ||
    (h == that.h && (m < that.m ||
                     (m == that.m && s < that.s) ||
                       (s == that.s && ms < that.ms)))
  }
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

  /** Current time */
      def now() : Time = {
               val now  = new java.util.Date //TODO: replace java.util.Date with java.util.Calendar to remove deprecated warnings
               new Time{
                 override val h: Int = now.getHours
                 override val m: Int = now.getMinutes
                 override val s: Int = now.getSeconds
                 override val ms: Int = 0
              } 
    }
}
