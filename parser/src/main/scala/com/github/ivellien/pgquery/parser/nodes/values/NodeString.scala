package com.github.ivellien.pgquery.parser.nodes.values

import com.github.ivellien.pgquery.parser.nodes.{Node, NodeDecoder}
import io.circe.Decoder
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import com.github.ivellien.pgquery.parser.nodes.Node.circeConfig

case class NodeString(
    str: String
) extends Value {
  override def query: String = str
}

object NodeString extends NodeDecoder[NodeString] {
  override implicit protected val vanillaDecoder: Decoder[NodeString] =
    deriveConfiguredDecoder[NodeString]
}
