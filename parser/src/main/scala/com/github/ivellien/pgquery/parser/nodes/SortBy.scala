package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.{SortByDir, SortByNulls}
import com.github.ivellien.pgquery.parser.nodes.Node.{
  circeConfig,
  optionToQuery
}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class SortBy(
    node: Option[Node],
    sortby_dir: SortByDir.Value,
    sortby_nulls: SortByNulls.Value,
    location: Option[Int],
    useOp: List[Node] = List.empty
) extends Node {
  override def query: String =
    s"${optionToQuery(node)}${sortby_dir.toString}"
}

object SortBy extends NodeDecoder[SortBy] {
  override implicit protected val vanillaDecoder: Decoder[SortBy] =
    deriveConfiguredDecoder[SortBy]
}
