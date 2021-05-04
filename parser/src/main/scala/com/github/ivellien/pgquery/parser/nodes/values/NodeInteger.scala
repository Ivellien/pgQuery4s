package com.github.ivellien.pgquery.parser.nodes.values

import com.github.ivellien.pgquery.parser.enums.NodeTag
import com.github.ivellien.pgquery.parser.nodes.{Node, NodeDecoder}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case class NodeInteger(
    ival: Int
) extends Value {
  override def query: String = ival.toString
}

object NodeInteger extends NodeDecoder[NodeInteger](NodeTag.T_Integer) {
  override implicit protected val vanillaDecoder: Decoder[NodeInteger] =
    deriveConfiguredDecoder[NodeInteger]
}
