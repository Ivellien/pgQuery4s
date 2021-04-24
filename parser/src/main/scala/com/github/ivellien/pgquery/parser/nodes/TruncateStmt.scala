package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.DropBehavior
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class TruncateStmt(
    restart_seqs: Option[Boolean],
    behavior: DropBehavior.Value,
    relations: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"TRUNCATE TABLE ${relations.map(_.query).mkString(", ")}"
}
