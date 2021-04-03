package com.github.ivellien.pgquery.nodes

import io.circe.generic.extras.ConfiguredJsonCodec
import com.github.ivellien.pgquery.nodes.Node.circeConfig

@ConfiguredJsonCodec(decodeOnly = true)
case class NodeInteger(
                        ival: Int
                      ) extends Node {
  override def query: String = ival.toString
}