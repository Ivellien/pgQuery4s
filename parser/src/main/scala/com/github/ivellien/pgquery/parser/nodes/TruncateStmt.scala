package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{DropBehavior, NodeTag}
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class TruncateStmt(
    restart_seqs: Option[Boolean],
    behavior: DropBehavior.Value,
    relations: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"TRUNCATE TABLE ${relations.map(_.query).mkString(", ")}"
}

object TruncateStmt extends NodeDecoder[TruncateStmt](NodeTag.T_TruncateStmt) {
  override implicit protected val vanillaDecoder: Decoder[TruncateStmt] =
    deriveConfiguredDecoder[TruncateStmt]
}
