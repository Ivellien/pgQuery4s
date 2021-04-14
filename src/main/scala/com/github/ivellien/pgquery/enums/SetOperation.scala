package com.github.ivellien.pgquery.enums

object SetOperation extends Enumeration with EnumerationDecoder {
  val SetOpNone: Value = Value("")
  val SetOpUnion: Value = Value("UNION")
  val SetOpIntersect: Value = Value("INTERSECT")
  val SetOpExcept: Value = Value("EXCEPT")
}
