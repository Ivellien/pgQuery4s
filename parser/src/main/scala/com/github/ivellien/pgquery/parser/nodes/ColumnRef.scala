package com.github.ivellien.pgquery.parser.nodes

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig
import com.github.ivellien.pgquery.parser.nodes.values.Value
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder

case class ColumnRef(
    fields: List[Value],
    location: Option[Int]
) extends Node {
  override def query: String = fields.map(_.query).mkString(".")
}

object ColumnRef extends NodeDecoder[ColumnRef](NodeTag.T_ColumnRef) {
  override implicit protected val vanillaDecoder: Decoder[ColumnRef] =
    deriveConfiguredDecoder[ColumnRef]
}
