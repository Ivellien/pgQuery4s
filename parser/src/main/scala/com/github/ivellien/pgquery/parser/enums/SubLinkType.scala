package com.github.ivellien.pgquery.parser.enums

object SubLinkType extends Enumeration with EnumerationDecoder {
  val ExistsSublink = Value("EXISTS")
  val AllSubLink = Value("ALL")
  val AnySubLink = Value("ANY")
  val RowcompareSubLink = Value("ROWCOMPARE")
  val ExprSubLink = Value("EXPR")
  val MultiExprSubLink = Value("MULTIEXP")
  val ArraySubLink = Value("ARRAY")
  val CreSubLink = Value("CTE")
}
