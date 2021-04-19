package com.github.ivellien.pgquery.parser.enums

object SortByDir extends Enumeration with EnumerationDecoder {
  val SortByDefault: Value = Value("")
  val SortByAsc: Value = Value(" ASC")
  val SortByDesc: Value = Value(" DESC")
  val SortByUsing: Value = Value(" USING")
}
