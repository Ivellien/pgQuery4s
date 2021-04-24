package com.github.ivellien.pgquery.parser.enums

object JoinType extends Enumeration with EnumerationDecoder {
  val JoinInner: Value = Value("INNER JOIN")
  val JoinLeft: Value = Value("LEFT JOIN")
  val JoinFull: Value = Value("FULL JOIN")
  val JoinRight: Value = Value("RIGHT JOIN")
  val JoinSemi: Value = Value("SEMI JOIN")
  val JoinAnti: Value = Value("ANTI JOIN")
  val JoinUniqueOuter: Value = Value("UNIQUE OUTER JOIN")
  val JoinUniqueInner: Value = Value("UNIQUE INNER JOIN")
}
