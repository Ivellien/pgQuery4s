package com.github.ivellien.pgquery.parser.enums

object BoolExprType extends Enumeration with EnumerationDecoder {
  val AndExpr = Value("and")
  val OrExpr = Value("or")
  val NotExpr = Value("not")
}
