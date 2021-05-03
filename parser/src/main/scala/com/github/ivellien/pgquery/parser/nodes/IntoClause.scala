package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.OnCommitAction
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class IntoClause(
    rel: Option[RangeVar],
    onCommit: Option[OnCommitAction.Value],
    tableSpaceName: Option[String],
    viewQuery: Option[Node],
    skipData: Option[Boolean],
    colNames: List[Node] = List.empty,
    options: List[Node] = List.empty
) extends Node {
  override def query: String = optionToQuery(rel)
}

object IntoClause extends NodeDecoder[IntoClause] {
  override implicit protected val vanillaDecoder: Decoder[IntoClause] =
    deriveConfiguredDecoder[IntoClause]
}
