package pl.metastack.metatime

object Locale {
  object English {
    case object America extends Locale
    case object GreatBritain extends Locale
  }

  object German {
    case object Germany extends Locale
    case object Switzerland extends Locale
    case object Austria extends Locale
  }

  case object Polish extends Locale

  def apply(isoCode: String): Locale = ???
}

trait Locale {
  def isoCode: String = ???
  def languageCode: String = ???
  def countryCode: String = ???
}
