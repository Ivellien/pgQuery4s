package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class DropdbStmt(
    dbname: Option[String],
    missing_ok: Option[Boolean]
) extends Node {
  override def query: String =
    dbname.map(name => s"DROP DATABASE $name").getOrElse("")
}
