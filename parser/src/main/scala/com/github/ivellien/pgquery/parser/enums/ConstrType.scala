package com.github.ivellien.pgquery.parser.enums

object ConstrType extends Enumeration with EnumerationDecoder {
  val ConstrNull: Value = Value("NULL")
  val ConstrNotnull: Value = Value("NOT NULL")
  val ConstrDefault: Value = Value("DEFAULT")
  val ConstrIdentity: Value = Value("IDENTITY")
  val ConstrCheck: Value = Value("CHECK")
  val ConstrPrimary: Value = Value("PRIMARY KEY")
  val ConstrUnique: Value = Value("UNIQUE")
  val ConstrExclusion: Value = Value("EXCLUSION")
  val ConstrForeign: Value = Value("FOREIGN KEY")
  val ConstrAttrDeferrable: Value = Value("ATTR_DEFERRABLE")
  val ConstrAttrNotDeferrable: Value = Value("ATTR_NOT_DEFERRABLE")
  val ConstrAttrDeferred: Value = Value("ATTR_DEFERRED")
  val ConstrAttrImmediate: Value = Value("ATTR_IMMEDIATE")
}
