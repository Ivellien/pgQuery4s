package com.github.ivellien.pgquery.enums

object JoinType extends Enumeration with EnumerationDecoder {
  val JoinInner = Value("INNER JOIN")
  val JoinLeft = Value("LEFT JOIN")
  val JoinFull = Value("FULL JOIN")
  val JoinRight = Value("RIGHT JOIN")
  val JoinSemi = Value("SEMI JOIN")
  val JoinAnti = Value("ANTI JOIN")
  val JoinUniqueOuter = Value("UNIQUE OUTER JOIN")
  val JoinUniqueInner = Value("UNIQUE INNER JOIN")
}
