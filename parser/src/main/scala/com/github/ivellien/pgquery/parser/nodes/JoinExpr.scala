package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.JoinType
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class JoinExpr(
    jointype: JoinType.Value,
    isNatural: Option[Boolean],
    larg: Option[Node],
    rarg: Option[Node],
    quals: Option[Node],
    alias: Option[Alias],
    rtindex: Option[Int],
    usingClause: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${optionToQuery(larg)} ${jointype.toString} ${optionToQuery(rarg)} ON ${optionToQuery(quals)}"
}

object JoinExpr extends NodeDecoder[JoinExpr] {
  override implicit protected val vanillaDecoder: Decoder[JoinExpr] =
    deriveConfiguredDecoder[JoinExpr]
}
