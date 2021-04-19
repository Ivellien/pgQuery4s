package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class RawStmt(
    stmtLocation: Option[Int],
    stmtLen: Option[Int],
    stmt: Option[Node]
) extends Node {
  override def query: String = optionToQuery(stmt)
}
