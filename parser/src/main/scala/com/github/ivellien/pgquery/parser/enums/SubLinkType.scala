package com.github.ivellien.pgquery.parser.enums

object SubLinkType extends Enumeration with EnumerationDecoder {
  val ExistsSublink: Value = Value("EXISTS")
  val AllSubLink: Value = Value("ALL")
  val AnySubLink: Value = Value("ANY")
  val RowcompareSubLink: Value = Value("ROWCOMPARE")
  val ExprSubLink: Value = Value("EXPR")
  val MultiExprSubLink: Value = Value("MULTIEXPR")
  val ArraySubLink: Value = Value("ARRAY")
  val CteSubLink: Value = Value("CTE")
}
