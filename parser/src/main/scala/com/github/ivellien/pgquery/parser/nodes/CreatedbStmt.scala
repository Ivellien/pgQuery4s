package com.github.ivellien.pgquery.parser.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class CreatedbStmt(
    dbname: Option[String],
    options: List[Node] = List.empty
) extends Node {
  override def query: String =
    dbname.map(name => s"CREATE DATABASE $name").getOrElse("")
}
