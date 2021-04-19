package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.enums.OnCommitAction
import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}

@ConfiguredJsonCodec(decodeOnly = true)
case class IntoClause(
    rel: Option[Node],
    onCommit: Option[OnCommitAction.Value],
    tableSpaceName: Option[String],
    viewQuery: Option[Node],
    skipData: Option[Boolean],
    colNames: List[Node] = List.empty,
    options: List[Node] = List.empty
) extends Node {
  override def query: String = optionToQuery(rel)
}
