package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class DropdbStmt(
    dbname: Option[String],
    missing_ok: Option[Boolean]
) extends Node {
  override def query: String =
    dbname.map(name => s"DROP DATABASE $name").getOrElse("")
}

object DropdbStmt extends NodeDecoder[DropdbStmt](NodeTag.T_DropdbStmt) {
  override implicit protected val vanillaDecoder: Decoder[DropdbStmt] =
    deriveConfiguredDecoder[DropdbStmt]
}
