package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.OnCommitAction
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class CreateStmt(
    relation: Option[RangeVar],
    partbound: Option[Node], // TODO PartitionBoundSpec
    partspec: Option[Node], // TODO PartitionSpec
    ofTypename: Option[TypeName],
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

object CreateStmt extends NodeDecoder[CreateStmt] {
  override implicit protected val vanillaDecoder: Decoder[CreateStmt] =
    deriveConfiguredDecoder[CreateStmt]
}
