package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.nodes.Node.circeConfig
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class RawStmt(
    stmtLocation: Option[Int],
    stmtLen: Option[Int],
    stmt: Option[Node]
) extends Node {
  override def query: String = stmt.map(node => node.query).getOrElse("")
}
