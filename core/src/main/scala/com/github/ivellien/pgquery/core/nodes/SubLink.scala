package com.github.ivellien.pgquery.core.nodes

import com.github.ivellien.pgquery.core.enums.SubLinkType
import com.github.ivellien.pgquery.core.nodes.Node.{circeConfig, optionToQuery}
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec(decodeOnly = true)
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
