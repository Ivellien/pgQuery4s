package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class RawStmt(
    stmtLocation: Option[Int],
    stmtLen: Option[Int],
    stmt: Option[Node]
) extends Node {
  override def query: String = optionToQuery(stmt)
}

object RawStmt extends NodeDecoder[RawStmt](NodeTag.T_RawStmt) {
  override implicit protected val vanillaDecoder: Decoder[RawStmt] =
    deriveConfiguredDecoder[RawStmt]
}
