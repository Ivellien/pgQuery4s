package com.github.ivellien.pgquery.core.enums

object OnCommitAction extends Enumeration with EnumerationDecoder {
  val OnCommitNoOp, OnCommitPreserveRows, OnCommitDeleteRows, OnCommitDrop =
    Value
}
