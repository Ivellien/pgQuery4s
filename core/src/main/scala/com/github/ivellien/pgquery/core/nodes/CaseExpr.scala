package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
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
