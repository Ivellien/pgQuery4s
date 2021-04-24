package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.ObjectType
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}

@ConfiguredJsonCodec(decodeOnly = true)
case class AlterTableStmt(
    relation: Option[Node], // RangeVar
    relkind: ObjectType.Value,
    missing_ok: Option[Boolean],
    cmds: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"ALTER TABLE ${optionToQuery(relation)} ${cmds.map(_.query).mkString(" ")}"
}
