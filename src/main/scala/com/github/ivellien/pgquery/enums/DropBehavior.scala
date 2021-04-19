package com.github.ivellien.pgquery.enums

object DropBehavior extends Enumeration with EnumerationDecoder {
  val DropRestrict, DropCascade = Value
}
