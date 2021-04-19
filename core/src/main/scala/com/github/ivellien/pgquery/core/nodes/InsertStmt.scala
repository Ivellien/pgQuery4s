package com.github.ivellien.pgquery.core.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class InsertStmt(
    relation: Option[Node],
    selectStmt: Option[Node],
    onConflictClause: Option[Node],
    withClause: Option[Node],
    overRide: Option[Node],
    cols: List[Node] = List.empty,
    returningList: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"INSERT INTO ${optionToQuery(relation)} (${cols.map(_.query).mkString(", ")}) ${optionToQuery(selectStmt)}"
}
