package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class CaseWhen(
    xpr: Option[Node],
    expr: Option[Node],
    result: Option[Node],
    location: Option[Int]
) extends Node {
  override def query: String =
    s"WHEN ${optionToQuery(expr)} THEN ${optionToQuery(result)}"
}

object CaseWhen extends NodeDecoder[CaseWhen] {
  override implicit protected val vanillaDecoder: Decoder[CaseWhen] =
    deriveConfiguredDecoder[CaseWhen]
}
