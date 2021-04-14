package com.github.ivellien.pgquery.enums

object NullTestType extends Enumeration with EnumerationDecoder {
  val IsNull: Value = Value("IS NULL")
  val IsNotNull: Value = Value("IS NOT NULL")
}
