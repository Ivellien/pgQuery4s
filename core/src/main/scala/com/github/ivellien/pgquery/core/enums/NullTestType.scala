package com.github.ivellien.pgquery.core.enums

object NullTestType extends Enumeration with EnumerationDecoder {
  val IsNull = Value("IS NULL")
  val IsNotNull = Value("IS NOT NULL")
}
