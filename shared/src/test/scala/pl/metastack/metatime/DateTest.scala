package pl.metastack.metatime

import org.scalatest.FunSuite

class DateTest extends FunSuite {
  import Implicits._

  test("construct") {
    val date = Date(2015, 12, 5)
    assert(date.year == 2015)
    assert(date.month == 12)
    assert(date.day == 5)
  }

  test("implicits") {
    assert(Year(2015) == 2015.year)
  }

  test("dayUnix") {
    assert(Day(1 + 1.0 / 24).unix().value == 90000000)
  }

  test("date") {
    assert(Year(2015).date == Date(2015, 1, 1))
    assert(Day(1) == Day(1))
  }

  test("add") {
    assert(Date(2015) + 1.day == Date(2015, 1, 2))
  }

  test("datePlus") {
    assert(Date(2015, 1, 1) + Date(2015, 1, 1) == Date(2060, 1, 2))
  }

  ignore("until") {
    assert((Year(2015) until Year(2016)) == ???)
  }

  ignore("now") {
    /** Current time */
    assert(DateTime.now() == ???)

    /** Unix timestamp */
    assert(DateTime.now().timestamp == ???)
  }

  test("fromNow") {
    assert(Date(2012, 1, 1).fromNow.format == "4 year(s) ago")
    assert(Date(2015, 8, 8).fromNow.format == "6 month(s) ago")
    assert(Date(2018, 10, 1).fromNow.format == "in 2 year(s)")
  }
}