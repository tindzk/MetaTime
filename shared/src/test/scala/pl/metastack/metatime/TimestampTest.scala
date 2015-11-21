package pl.metastack.metatime

import minitest.SimpleTestSuite

object TimestampTest extends SimpleTestSuite with TestIgnored {
  testIgnored("construct") {
    assertEquals(Unix(???).date, ???)
    assertEquals(Unix(???).dateTime, ???)
  }
}
