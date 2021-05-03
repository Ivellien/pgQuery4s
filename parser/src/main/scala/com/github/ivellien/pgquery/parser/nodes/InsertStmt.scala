package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class InsertStmt(
    relation: Option[RangeVar],
    selectStmt: Option[SelectStmt],
    onConflictClause: Option[Node], // TODO OnConflictClause
    withClause: Option[Node], // TODO WithClause
    overRide: Option[Node], // TODO OverridingKind
    cols: List[Node] = List.empty,
    returningList: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"INSERT INTO ${optionToQuery(relation)} (${cols.map(_.query).mkString(", ")}) ${optionToQuery(selectStmt)}"
}

object InsertStmt extends NodeDecoder[InsertStmt] {
  override implicit protected val vanillaDecoder: Decoder[InsertStmt] =
    deriveConfiguredDecoder[InsertStmt]
}
