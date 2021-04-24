package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.OnCommitAction
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}

@ConfiguredJsonCodec(decodeOnly = true)
case class CreateStmt(
    relation: Option[Node], // RangeVar
    partbound: Option[Node], // PartitionBoundSpec
    partspec: Option[Node], // PartitionSpec
    ofTypename: Option[Node], // TypeName
    oncommit: OnCommitAction.Value,
    tablespacename: Option[String],
    ifNotExists: Option[Boolean],
    tableElts: List[Node] = List.empty,
    inhRelations: List[Node] = List.empty,
    constraints: List[Node] = List.empty,
    options: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"CREATE TABLE ${optionToQuery(relation)} (${tableElts.map(_.query).mkString(", ")})"
}
