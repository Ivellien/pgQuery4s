package com.github.ivellien.pgquery.parser.enums

object NullTestType extends Enumeration with EnumerationDecoder {
  val IsNull = Value("IS NULL")
  val IsNotNull = Value("IS NOT NULL")
}
