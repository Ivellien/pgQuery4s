package com.github.ivellien.pgquery.enums

object BoolExprType extends Enumeration with EnumerationDecoder {
  val AndExpr: Value = Value("and")
  val OrExpr: Value = Value("or")
  val NotExpr: Value = Value("not")
}
