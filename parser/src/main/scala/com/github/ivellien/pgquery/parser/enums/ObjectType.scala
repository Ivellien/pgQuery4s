package com.github.ivellien.pgquery.parser.enums

object ObjectType extends Enumeration with EnumerationDecoder {
  val ObjectAccessMethod,
  ObjectAggregate,
  ObjectAmop,
  ObjectAmproc,
  ObjectAttribute,
  ObjectCast,
  ObjectColumn,
  ObjectCollation,
  ObjectConversion,
  ObjectDatabase,
  ObjectDefault,
  ObjectDefacl,
  ObjectDomain,
  ObjectDomconstraint,
  ObjectEventTrigger,
  ObjectExtension,
  ObjectFdw,
  ObjectForeignServer,
  ObjectForeignTable,
  ObjectFunction,
  ObjectIndex,
  ObjectLanguage,
  ObjectLargeobject,
  ObjectMatview,
  ObjectOpclass,
  ObjectOperator,
  ObjectOpfamily,
  ObjectPolicy,
  ObjectPublication,
  ObjectPublicationRel,
  ObjectRole,
  ObjectRule,
  ObjectSchema,
  ObjectSequence,
  ObjectSubscription,
  ObjectStatisticExt,
  ObjectTabconstraint,
  ObjectTable,
  ObjectTablespace,
  ObjectTransform,
  ObjectTrigger,
  ObjectTsconfiguration,
  ObjectTsdictionary,
  ObjectTsparser,
  ObjectTstemplate,
  ObjectType,
  ObjectUserMapping,
  ObjectView = Value
}
