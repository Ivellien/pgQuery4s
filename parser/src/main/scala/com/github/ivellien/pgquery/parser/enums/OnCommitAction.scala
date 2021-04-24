package com.github.ivellien.pgquery.parser.enums

object OnCommitAction extends Enumeration with EnumerationDecoder {
  val OnCommitNoOp, OnCommitPreserveRows, OnCommitDeleteRows, OnCommitDrop =
    Value
}
