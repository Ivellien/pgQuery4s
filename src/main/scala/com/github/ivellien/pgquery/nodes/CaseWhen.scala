package com.github.ivellien.pgquery.nodes

import com.github.ivellien.pgquery.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
case class CaseWhen(
    xpr: Option[Node],
    expr: Option[Node],
    result: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String =
    s"WHEN ${optionToQuery(expr)} THEN ${optionToQuery(result)}"
}
