package com.github.ivellien.pgquery.core.enums

object SortByNulls extends Enumeration with EnumerationDecoder {
  val SortByNullsDefault, SortByNullsFirst, SortByNullsLast = Value
}
