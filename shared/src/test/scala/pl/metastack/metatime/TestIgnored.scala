package pl.metastack.metatime

trait TestIgnored {
  def testIgnored(s: String)(f: => Unit): Unit =
    println(s"Test '$s' ignored")
}
