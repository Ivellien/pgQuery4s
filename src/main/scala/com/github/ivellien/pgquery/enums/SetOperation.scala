package com.github.ivellien.pgquery.enums

object SetOperation extends Enumeration with EnumerationDecoder {
  val SetOpNone = Value("")
  val SetOpUnion = Value("UNION")
  val SetOpIntersect = Value("INTERSECT")
  val SetOpExcept = Value("EXCEPT")
}
