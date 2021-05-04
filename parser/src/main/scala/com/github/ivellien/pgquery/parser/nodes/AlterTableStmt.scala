package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{NodeTag, ObjectType}
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class AlterTableStmt(
    relation: Option[RangeVar], // RangeVar
    relkind: ObjectType.Value,
    missing_ok: Option[Boolean],
    cmds: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"ALTER TABLE ${optionToQuery(relation)} ${cmds.map(_.query).mkString(" ")}"
}

object AlterTableStmt
    extends NodeDecoder[AlterTableStmt](NodeTag.T_AlterTableStmt) {
  override implicit protected val vanillaDecoder: Decoder[AlterTableStmt] =
    deriveConfiguredDecoder[AlterTableStmt]
}
