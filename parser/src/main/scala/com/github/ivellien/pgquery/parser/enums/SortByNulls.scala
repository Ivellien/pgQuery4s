package com.github.ivellien.pgquery.parser.enums

object SortByNulls extends Enumeration with EnumerationDecoder {
  val SortByNullsDefault, SortByNullsFirst, SortByNullsLast = Value
}
