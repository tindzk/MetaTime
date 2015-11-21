package pl.metastack.metatime

import minitest.SimpleTestSuite

object LocaleTest extends SimpleTestSuite with TestIgnored {
  testIgnored("construct") {
    assertEquals(Locale("en_GB"), Locale.English.GreatBritain)
    assertEquals(Locale("pl_PL"), Locale.Polish)
  }
}
