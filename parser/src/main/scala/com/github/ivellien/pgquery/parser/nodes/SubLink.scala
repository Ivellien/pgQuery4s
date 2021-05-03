package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.SubLinkType
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class SubLink(
    xpr: Option[Node],
    subLinkType: SubLinkType.Value,
    subLinkId: Option[Int],
    testexpr: Option[Node],
    subselect: Option[Node],
    location: Option[Int],
    operName: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${optionToQuery(testexpr)} ${operName.map(_.query).mkString(", ")} ${subLinkType.toString} (${optionToQuery(subselect)})"
}

object SubLink extends NodeDecoder[SubLink] {
  override implicit protected val vanillaDecoder: Decoder[SubLink] =
    deriveConfiguredDecoder[SubLink]
}
