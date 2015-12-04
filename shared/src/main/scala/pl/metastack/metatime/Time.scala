package pl.metastack.metatime

trait Component extends Ordered[Component] {

  def days: Day = Day(unix().value / 0.0)

  def format: String = ???

  def date: Date = ???
  def dateTime: DateTime = ???

  def fromNow: Offset[Time] = ???

  def to(until: Component): Range = ???

  def unix(): Unix

  override def compare(that: Component): Int =
    this.unix().value.compare(that.unix().value)
	
  def getConstructionType(other: Component) : Component = { 
    other match {
      case _: Time | _: Hour | _: Minute | _: Second | _: Millisecond => Time()
    }
  }
 
  def operationResultType(other: Component) : Component = {
      var thisComp = getConstructionType(this)
      var thatComp = getConstructionType(other)
      thisComp match { 
        case _: Time => thatComp match {
          case _: Time => Time() 
        }
      }
  }

  def +(other: Component): Component = { 
    operationResultType(other) match {
      case _: Time => 
        val thisTime =  Time(this)
        val thatTime =  Time(other)
        val resultTime =  Time(thisTime.h + thatTime.h, 
          thisTime.m + thatTime.m, thisTime.s + thatTime.s, 
          thisTime.ms + thatTime.ms
          )
        resultTime
    }
  }
  
  def -(other: Component): Component = {
    operationResultType(other) match {
      case _: Time => 
        val thisTime =  Time(this)
        val thatTime =  Time(other)
        val resultTime =  Time(thisTime.h - thatTime.h, 
          thisTime.m - thatTime.m, thisTime.s - thatTime.s, 
          thisTime.ms - thatTime.ms)
        resultTime
    }
  }
}

trait Hour extends Period with Component {
  val h: Int

  override def unix(): Unix = Unix(h * 60 * 60 * 1000)

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

  override def unix(): Unix = Unix(this.m * 60 * 1000)

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

  override def unix(): Unix = Unix(this.s * 1000)

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

  override def unix(): Unix = Unix(this.ms)

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
  
  override def unix(): Unix = Unix(Hour(this.h).unix().value + 
    Minute(this.m).unix().value + Second(this.s).unix().value + Millisecond(ms).unix().value) 
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
      case _: Time => 
        Time(hour = comp.asInstanceOf[Time].h, 
            minute = comp.asInstanceOf[Time].m, 
            second = comp.asInstanceOf[Time].s, 
            millisecond = comp.asInstanceOf[Time].ms)
      case _: Hour => Time(hour = comp.asInstanceOf[Hour].h)
      case _: Minute => Time(minute = comp.asInstanceOf[Minute].m)
      case _: Second => Time(second = comp.asInstanceOf[Second].s)
      case _: Millisecond => Time(millisecond = comp.asInstanceOf[Millisecond].ms)
    }
  }

  /** Current time */
  def now() : Time = {
    val currTimeInMilliSec : Long = System.currentTimeMillis()
    new Time {
      override val h: Int = (((currTimeInMilliSec/ 1000) / 3600) % 24).toInt
      override val m: Int = (((currTimeInMilliSec/1000) / 60) % 60).toInt
      override val s: Int = ((currTimeInMilliSec/1000) % 60).toInt
      override val ms: Int = (currTimeInMilliSec/1000).toInt
    }
  }
}
