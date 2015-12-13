package pl.metastack.metatime

import org.scalatest.FunSuite

class LocaleTest extends FunSuite {
  ignore("construct") {
    assert(Locale("en_GB") == Locale.English.GreatBritain)
    assert(Locale("pl_PL") == Locale.Polish)
  }
}
