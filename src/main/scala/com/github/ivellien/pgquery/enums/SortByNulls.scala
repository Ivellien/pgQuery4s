package com.github.ivellien.pgquery.enums

object SortByNulls extends Enumeration with EnumerationDecoder {
  val SortByNullsDefault, SortByNullsFirst, SortByNullsLast = Value
}
