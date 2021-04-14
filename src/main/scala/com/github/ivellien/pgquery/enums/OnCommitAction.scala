package com.github.ivellien.pgquery.enums

object OnCommitAction extends Enumeration with EnumerationDecoder {
  val OnCommitNoOp, OnCommitPreserveRows, OnCommitDeleteRows, OnCommitDrop =
    Value
}
