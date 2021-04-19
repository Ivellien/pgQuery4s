package com.github.ivellien.pgquery.parser.enums

object DropBehavior extends Enumeration with EnumerationDecoder {
  val DropRestrict, DropCascade = Value
}
