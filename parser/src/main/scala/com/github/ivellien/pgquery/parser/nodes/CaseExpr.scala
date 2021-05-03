package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class CaseExpr(
    xpr: Option[Node],
    casetype: Option[Int],
    casecollid: Option[Int],
    arg: Option[Node],
    defresult: Option[Node],
    location: Option[Int],
    args: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"(CASE ${args.map(_.query).mkString(" ")} ELSE ${optionToQuery(defresult)} END)"
}

object CaseExpr extends NodeDecoder[CaseExpr] {
  override implicit protected val vanillaDecoder: Decoder[CaseExpr] =
    deriveConfiguredDecoder[CaseExpr]
}
