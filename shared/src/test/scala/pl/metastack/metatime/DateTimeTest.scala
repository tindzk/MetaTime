package pl.metastack.metatime

import org.scalatest.FunSuite

class DateTimeTest extends FunSuite {
  import Implicits._

  test("construct") {
    val dateTime = DateTime(2016, 1, 1, 10, 10, 50, 500)
    assert(dateTime.year == 2016)
    assert(dateTime.month == 1)
    assert(dateTime.day == 1)
    assert(dateTime.h == 10)
    assert(dateTime.m == 10)
    assert(dateTime.s == 50)
    assert(dateTime.ms == 500)
  }

  test("date") {
    assert(Year(2015).dateTime == DateTime(2015, 1, 1, 0, 0, 0))
  }

  test("dateTimePlus") {
    assert(DateTime(2016,1,1,1,1,1,1) + Day(1) == DateTime(2016, 1, 2, 1, 1, 1, 1))
  }

  ignore("now") {
    assert(DateTime.now() == ???)
  }

  ignore("format") {
    val dateTime = DateTime(2015, 1, 1)
    //assert(dateTime.format == ???)
    //assert(dateTime.format("MMM d, yyyy") == ???)

    val dateTimeOffset = DateTime(2015, 1, 1).fromNow
    //assert(dateTimeOffset.format == s"${???} days from now")
  }

  ignore("Custom formatter") {
    val formatter = Formatter.branch(Seq(
      (0.seconds to 1.minute) -> Formatter.JustNow,
      (1.minute to 1.hour) -> Formatter.Ago(Formatter.Minutes),
      (1.hour to 1.day) -> Formatter.Ago(Formatter.Hours),
      (1.day to 1.week) -> Formatter.Ago(Formatter.Days)),
      Formatter.DateTime(
        "%monthShort% %dayShort%, %yearLong% %hour%:%minute% %amPm%"))

    assert(formatter.format(DateTime.now()) == "just now")
  }

  test("fromNow") {
    assert(DateTime(2018, 10, 1, 1, 1, 1, 1).fromNow().format =="in 2 year(s)")
    assert(DateTime(2013, 2, 1, 1, 1, 1, 1).fromNow().format =="3 year(s) ago")
  }

}