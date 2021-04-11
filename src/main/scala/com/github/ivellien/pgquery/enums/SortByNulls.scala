package com.github.ivellien.pgquery.enums

import io.circe.{Decoder, DecodingFailure}

object SortByNulls extends Enumeration with EnumerationDecoder {
  val SortByNullsDefault, SortByNullsFirst, SortByNullsLast = Value
}
