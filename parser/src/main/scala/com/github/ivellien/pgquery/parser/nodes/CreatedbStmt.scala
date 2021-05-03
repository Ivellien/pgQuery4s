package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class CreatedbStmt(
    dbname: Option[String],
    options: List[Node] = List.empty // TODO List[DefElem]
) extends Node {
  override def query: String =
    dbname.map(name => s"CREATE DATABASE $name").getOrElse("")
}

object CreatedbStmt extends NodeDecoder[CreatedbStmt] {
  override implicit protected val vanillaDecoder: Decoder[CreatedbStmt] =
    deriveConfiguredDecoder[CreatedbStmt]
}
