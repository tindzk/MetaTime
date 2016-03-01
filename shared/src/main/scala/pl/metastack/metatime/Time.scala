package pl.metastack.metatime

trait Hour extends Component {
  val h: Int

  override def unix(): Unix = Unix(h * Minute(60).unix().value)

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

trait Minute extends Component {
  val m: Int

  override def unix(): Unix = Unix(m * Second(60).unix().value)

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

trait Second extends Component {
  val s: Int

  override def unix(): Unix = Unix(s * Millisecond(1000).unix().value)

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

trait Millisecond extends Component {
  val ms: Int

  override def unix(): Unix = Unix(ms)

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

trait Time extends Hour with Minute with Second with Millisecond {

  override def equals(that: Any): Boolean =
    that match {
      case other: Time => other.h == h && other.m == m && other.s == s && other.ms == ms
      case _ => false
    }

  override def unix(): Unix = Unix(
    Hour(h).unix().value + 
    Minute(m).unix().value +
    Second(s).unix().value + 
    Millisecond(ms).unix().value)
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

  def apply(comp : Component) : Time = {
    comp match {
      case t: Time =>
        Time(hour = t.h,
        minute = t.m,
        second = t.s,
        millisecond = t.ms)
      case hr: Hour => Time(hour = hr.h)
      case m: Minute => Time(minute = m.m)
      case s: Second => Time(second = s.s)
      case ms: Millisecond => Time(millisecond = ms.ms)
    }
  }

  def apply(milliseconds: Long): Time = {
    val hour = ((milliseconds /
      (Hour(1).unix().value)).toInt) % 24
    val minute = ((milliseconds %
      (Hour(1).unix().value)) /
      (Minute(1).unix().value)).toInt
    val second = (((milliseconds %
      (Hour(1).unix().value)) %
      (Minute(1).unix().value)) /
      Second(1).unix().value).toInt
    val millisecond = ((((milliseconds %
      (Hour(1).unix().value)) %
      (Minute(1).unix().value)) %
      Second(1).unix().value) /
      Millisecond(1).unix().value).toInt
    Time(hour, minute, second, millisecond)
  }

  /** Current time */
  def now(): Time = {
    val currTimeInMilliSec: Long = System.currentTimeMillis()
    new Time {
      override val h: Int = (((currTimeInMilliSec / 1000) / 3600) % 24).toInt
      override val m: Int = (((currTimeInMilliSec / 1000) / 60) % 60).toInt
      override val s: Int = ((currTimeInMilliSec / 1000) % 60).toInt
      override val ms: Int = (currTimeInMilliSec % 1000).toInt
    }
  }
}
